package com.example.styledmap.Models;

/**
 * Created by ayush on 1/18/17.
 */

public class RMAP {

    CategoryListingResponse categoryListingResponse;
    SBDListingResponse sbdListingResponse;
    SKUListingResponse skuListingResponse;
    ClassificationListingResponse classificationListingResponse;

    public RMAP(CategoryListingResponse categoryListingResponse,
                SBDListingResponse sbdListingResponse,
                SKUListingResponse skuListingResponse,
                ClassificationListingResponse classificationListingResponse) {
        this.categoryListingResponse = categoryListingResponse;
        this.sbdListingResponse = sbdListingResponse;
        this.skuListingResponse = skuListingResponse;
        this.classificationListingResponse = classificationListingResponse;
    }

    public CategoryListingResponse getCategoryListingResponse() {
        return categoryListingResponse;
    }

    public void setCategoryListingResponse(CategoryListingResponse categoryListingResponse) {
        this.categoryListingResponse = categoryListingResponse;
    }

    public SBDListingResponse getSbdListingResponse() {
        return sbdListingResponse;
    }

    public void setSbdListingResponse(SBDListingResponse sbdListingResponse) {
        this.sbdListingResponse = sbdListingResponse;
    }

    public SKUListingResponse getSkuListingResponse() {
        return skuListingResponse;
    }

    public void setSkuListingResponse(SKUListingResponse skuListingResponse) {
        this.skuListingResponse = skuListingResponse;
    }

    public ClassificationListingResponse getClassificationListingResponse() {
        return classificationListingResponse;
    }

    public void setClassificationListingResponse(ClassificationListingResponse classificationListingResponse) {
        this.classificationListingResponse = classificationListingResponse;
    }

    @Override
    public String toString() {
        return "Category Listing : "+getCategoryListingResponse()+"\n"+
               "SBD Listing : " + getSbdListingResponse()+"\n"+
               "SKU Listing : " + getSkuListingResponse()+"\n"+
               "Classification Listing : " + getClassificationListingResponse()+"\n"+
               "------------------------------------------------------------------";
    }
}
