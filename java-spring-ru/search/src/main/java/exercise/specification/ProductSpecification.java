package exercise.specification;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Component;

import exercise.dto.ProductParamsDTO;
import exercise.model.Product;

// BEGIN
@Component
public class ProductSpecification {

    public Specification<Product> build(ProductParamsDTO params) {
        return withCategoryId(params.getCategoryId())
                .and(withRatingGt(params.getRatingGt()))
                .and(withPriceGt(params.getPriceGt()))
                .and(withPriceLt(params.getPriceLt()))
                .and(withTitleCont(params.getTitleCont()));
    }

    public Specification<Product> withCategoryId(Long categoryId) {
        return ((root, query, cb) -> categoryId == null ? cb.conjunction() : cb.equal(root.get("category").get("id"), categoryId));
    }

    public Specification<Product> withPriceLt(Integer priceLt) {
        return ((root, query, cb) -> priceLt == null ? cb.conjunction() : cb.greaterThan(root.get("price"), priceLt));
    }

    public Specification<Product> withPriceGt(Integer priceGt) {
        return ((root, query, cb) -> priceGt == null ? cb.conjunction() : cb.lessThan(root.get("price"), priceGt));
    }

    public Specification<Product> withRatingGt(Double ratingGt) {
        return ((root, query, cb) -> ratingGt == null ? cb.conjunction() : cb.greaterThan(root.get("rating"), ratingGt));
    }

    //Фильтрация по: Название товара содержит указанную подстроку без учета регистра
    // Метод like в JPA Criteria API используется для выполнения поиска по строкам с использованием шаблонов. Он позволяет находить строки,
    // которые соответствуют определенному шаблону,
    // включая подстроки. Этот метод часто используется для выполнения операций поиска с учетом символов подстановки.
    public Specification<Product> withTitleCont(String titleCont) {
        return ((root, query, cb) -> titleCont == null ? cb.conjunction() : cb.like(cb.lower(root.get("title")), titleCont.toLowerCase()));
    }
}
// END