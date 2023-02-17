package com.ecommercebackend.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import com.ecommercebackend.dto.Category;
import com.ecommercebackend.dto.Image;
import com.ecommercebackend.dto.Product;
import com.ecommercebackend.dto.ProductVariant;
import com.ecommercebackend.dto.Dimensions;
import com.ecommercebackend.dto.VariantSize;
import com.ecommercebackend.exception.BadRequestException;
import com.ecommercebackend.exception.NotFoundException;
import com.ecommercebackend.mappers.ImageMapper;
import com.ecommercebackend.mappers.ProductDetailMapper;
import com.ecommercebackend.mappers.ProductMapper;
import com.ecommercebackend.mappers.ProductVariantMapper;
import com.ecommercebackend.mappers.SizeMapper;
import com.ecommercebackend.mappers.VariantSizeMapper;
import com.ecommercebackend.payload.request.product.CreateNewProductRequest;
import com.ecommercebackend.payload.request.product.UpdateProductRequest;
import com.ecommercebackend.payload.request.size.SizeRequest;
import com.ecommercebackend.payload.response.NoData;
import com.ecommercebackend.payload.response.SuccessResponse;
import com.ecommercebackend.payload.response.product.ListProductAdminWithPaginateResponse;
import com.ecommercebackend.payload.response.product.ProductAdminResponse;
import com.ecommercebackend.payload.response.productdetail.ProductDetailAdminResponse;
import com.ecommercebackend.repository.CategoryRepository;
import com.ecommercebackend.repository.ImageRepository;
import com.ecommercebackend.repository.ProductRepository;
import com.ecommercebackend.repository.ProductVariantRepository;
import com.ecommercebackend.repository.VariantSizeRepository;
import com.ecommercebackend.service.ProductAdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;



@Service
//@Cacheable
public class ProductAdminServiceImpl implements ProductAdminService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private VariantSizeRepository variantSizeRepository;
    @Autowired
    private ProductVariantRepository productVariantRepository;
    @Autowired
    private CategoryRepository categoryRepository;
    @Autowired
    private ProductMapper productMapper;
    @Autowired
    private ProductDetailMapper productDetailMapper;
    @Autowired
    private ProductVariantMapper productVariantMapper;
    @Autowired
    private ImageMapper imageMapper;
    @Autowired
    private VariantSizeMapper variantSizeMapper;
    @Autowired
    private SizeMapper sizeMapper;

    @Override
    public SuccessResponse<ListProductAdminWithPaginateResponse> getAllProductWithPaginate(int limit, int offset) {
        Page<Product> productPage = productRepository.findAll(PageRequest.of(offset, limit, Sort.by("id").descending()));
        List<ProductAdminResponse> productAdminResponseList = productPage.stream()
                .filter(product -> !product.isDeleted())
                .map(productMapper::toProductAdminResponse).collect(Collectors.toList());
        ListProductAdminWithPaginateResponse listProductAdminWithPaginateResponse =
                ListProductAdminWithPaginateResponse.builder()
                        .products(productAdminResponseList)
                        .totalRows(productPage.getTotalElements())
                        .totalPages(productPage.getTotalPages()).build();
        return new SuccessResponse<>(listProductAdminWithPaginateResponse, "Get list products success!");
    }


    @Override
    @CacheEvict(value = "product", allEntries = true)
    public SuccessResponse<NoData> createNewProduct(CreateNewProductRequest createNewProductRequest) throws BadRequestException {
        Optional<Product> productDBOptFindByName = productRepository.findByName(createNewProductRequest.getName().toUpperCase());
        if (productDBOptFindByName.isPresent()) {
            throw new BadRequestException("Product name already exists");
        }
        List<VariantSize> listVariantSizeDBOptFindBySku = variantSizeRepository
                .findBySkuStartsWith(createNewProductRequest.getColors().get(0).getSku());
        if (listVariantSizeDBOptFindBySku.size() > 0) {
            throw new BadRequestException("Sku already exist");
        }

        Product product = productMapper.toProduct(createNewProductRequest);
        Product productDB = productRepository.save(product);

        List<ProductVariant> productVariants = createNewProductRequest.getColors().stream()
                .map(productVariantOfCreateNewProduct -> productVariantMapper.
                        toProductVariant(productVariantOfCreateNewProduct.getColor(), productDB))
                .collect(Collectors.toList());
        List<ProductVariant> productVariantsDB = productVariantRepository.saveAll(productVariants);

        List<Image> images = new ArrayList<>();
        List<VariantSize> variantSizes = new ArrayList<>();
        for (int i = 0; i < createNewProductRequest.getColors().size(); i++) {
            images.addAll(imageMapper.
                    toListImagesWithProductVariant(
                            createNewProductRequest.getColors().get(i).getImages(),
                            productVariantsDB.get(i)));
            variantSizes.addAll(variantSizeMapper.
                    toListVariantSizeWithProductVariant(
                            createNewProductRequest.getColors().get(i).getSizes(),
                            productVariantsDB.get(i),
                            createNewProductRequest.getColors().get(i).getSku(),
                            createNewProductRequest.getColors().get(i).getColor().getName()));
        }
        imageRepository.saveAll(images);
        variantSizeRepository.saveAll(variantSizes);
        return new SuccessResponse<>(NoData.builder().build(), "Created new product successfully.");
    }

    @Override
    public SuccessResponse<ProductDetailAdminResponse> getProductDetailByProductId(Integer productId) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findByIsDeletedFalseAndId(productId);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Product with this id doesn't exist.");
        }
        ProductDetailAdminResponse productDetailAdminResponse = productDetailMapper.toProductDetailAdminResponse(productOptional.get());
        return new SuccessResponse<>(productDetailAdminResponse, "Get product detail successful.");
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public SuccessResponse<NoData> deleteProductByProductId(Integer productId) throws NotFoundException {
        Optional<Product> productOptional = productRepository.findByIsDeletedFalseAndId(productId);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Don't exist product with this id.");
        }
        Product product = productOptional.get();
        product.setDeleted(true);
        productRepository.save(product);
        return new SuccessResponse<>(NoData.builder().build(), "Delete product successful.");
    }

    @Override
    @CacheEvict(value = "product", allEntries = true)
    public SuccessResponse<NoData> updateProductByProductId(Integer productId, UpdateProductRequest updateProductRequest) throws NotFoundException, BadRequestException {
        if (updateProductRequest.getDiscountPrice() > 0) {
            if (updateProductRequest.getDiscountPrice() > updateProductRequest.getPrice()) {
                throw new BadRequestException("Discount price must be lower than price.");
            }
            if (updateProductRequest.getStartDateDiscount() == null) {
                throw new BadRequestException("Start date discount is required when discount price greater than 0.");
            }
            if (updateProductRequest.getStartDateDiscount().before(new Date())) {
                throw new BadRequestException("Start date discount must after today.");
            }
            if (updateProductRequest.getEndDateDiscount() == null) {
                throw new BadRequestException("End date discount is required when discount price greater than 0.");
            }
            if (updateProductRequest.getStartDateDiscount().after(updateProductRequest.getEndDateDiscount())) {
                throw new BadRequestException("Start date discount must before end date discount.");
            }
        }
        Optional<Product> productOptional = productRepository.findByIsDeletedFalseAndId(productId);
        if (productOptional.isEmpty()) {
            throw new NotFoundException("Product with this id doesn't exist.");
        }
        Optional<Product> productDBOptFindByName = productRepository.findByName(updateProductRequest.getName().trim().toUpperCase());
        if (productDBOptFindByName.isPresent()
                && productDBOptFindByName.get().getId() != productOptional.get().getId()) {
            throw new BadRequestException("Product name already exists.");
        }
        Optional<Category> categoryOptional = categoryRepository.findById(updateProductRequest.getCategoryId());
        if (categoryOptional.isEmpty()) {
            throw new BadRequestException("Don't exist category with this id.");
        }

        Product product = productOptional.get();
        Set<ProductVariant> productVariants = product.getProductVariants();
        Set<VariantSize> variantSizes = new HashSet<>();
        for (ProductVariant item : productVariants) {
            variantSizes.addAll(item.getVariantSizes());
        }

        List<VariantSize> currentProductSize = new ArrayList<>(variantSizes);
        HashMap<Integer, Dimensions> requestProductSize = new HashMap<>();
        for (SizeRequest item : updateProductRequest.getSizes()) {
            requestProductSize.put(item.getId(), sizeMapper.toSize(item));
        }

        List<VariantSize> listVariantSizesDelete = new ArrayList<>();
        List<VariantSize> listVariantSizesAddNew = new ArrayList<>();
        for (VariantSize variantSize : currentProductSize) {
            Dimensions findSize = requestProductSize.get(variantSize.getSize().getId());
            if (findSize != null) {
                // Todo: Check this size in any order ? throw exception : add it to delete list;
            }
        }

        for (Map.Entry<Integer, Dimensions> entry : requestProductSize.entrySet()) {
            List<VariantSize> findVariantSize =
                    currentProductSize.stream().filter(item -> item.getSize().getId() == entry.getKey())
                            .collect(Collectors.toList());
            if (findVariantSize.size() == 0) {
                listVariantSizesAddNew.addAll(variantSizeMapper.toListVariantSizeWithProductVariant(
                        entry.getValue(), productVariants, updateProductRequest.getSku()));
            }
        }

        product.setId(updateProductRequest.getId());
        product.setName(updateProductRequest.getName());
        product.setPrice(updateProductRequest.getPrice());
        product.setDiscountPrice(updateProductRequest.getDiscountPrice());
        product.setStartDateDiscount(updateProductRequest.getStartDateDiscount());
        product.setEndDateDiscount(updateProductRequest.getEndDateDiscount());
        product.setDescription(updateProductRequest.getDescription());
        product.setPrimaryImageName(updateProductRequest.getPrimaryImageName());
        product.setPrimaryImageUrl(updateProductRequest.getPrimaryImageUrl());
        product.setSecondaryImageName(updateProductRequest.getSecondaryImageName());
        product.setSecondaryImageUrl(updateProductRequest.getSecondaryImageUrl());
        product.setVisible(updateProductRequest.isVisible());
        product.setUpdatedAt(new Date());
        product.setCategory(categoryOptional.get());
        productRepository.save(product);

        if (listVariantSizesDelete.size() > 0) {
            variantSizeRepository.deleteAll(listVariantSizesDelete);
        }
        if (listVariantSizesAddNew.size() > 0) {
            variantSizeRepository.saveAll(listVariantSizesAddNew);
        }
        return new SuccessResponse<>(NoData.builder().build(), "Update product success.");
    }
}
