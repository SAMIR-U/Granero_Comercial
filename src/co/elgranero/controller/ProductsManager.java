package co.elgranero.controller;

import co.elgranero.net.Product;

import java.io.IOException;
import java.util.ArrayList;

import co.elgranero.net.Category;
import co.elgranero.net.Subcategory;
import co.elgranero.persistence.SqlInstructionsReader;
import co.elgranero.net.Presentation;
import co.elgranero.net.ProductPresentation;

public class ProductsManager {
    private SqlInstructionsReader sir;
    public ProductsManager() throws IOException{
        this.sir = SqlInstructionsReader.getInstance();
    }
    
    public boolean registCategory(String categoryName) {
        return false;
    }
    public boolean registSubcategory(int categoryId, String subcategoryName) {
        return false;
    }
    public boolean registProduct(int subcategoryId, String name, String description, String expirationDate) {
        return false;
    }
    public boolean registPresentation(String presentationName) {
        return false;
    }
    public boolean registProductPresentation(int presentationId, int productId, double presentationPrice) {
        return false;
    }
    
    public ArrayList<Category> obtainCategories() {
        return null;
    }
    public ArrayList<Subcategory> obtainSubcategories() {
        return null;
    }
    public ArrayList<Product> obtainProducts() {
        return null;
    }
    public ArrayList<Presentation> obtainPresentations() {
        return null;
    }
    public ArrayList<ProductPresentation> obtainProductPresentations(int productId) {
        return null;
    }

    public boolean modifyCategory(Category category) {
        return false;
    }
    public boolean modifySubcategory(Subcategory subcategory) {
        return false;
    }
    public boolean modifyProduct(Product product) {
        return false;
    }
    public boolean modifyPresentation(Presentation presentation) {
        return false;
    }
    public boolean modifyProductPresentation(ProductPresentation productPresentation) {
        return false;
    }

    public boolean deleteCategory(int id) {
        return false;
    }
    public boolean deleteSubcategory(int id) {
        return false;
    }
    public boolean deleteProduct(int id) {
        return false;
    }
    public boolean deletePresentation(int id) {
        return false;
    }
    public boolean deleteProductPresentation(int presentationId, int productId) {
        return false;
    }
}
