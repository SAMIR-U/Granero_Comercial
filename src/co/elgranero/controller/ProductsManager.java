package co.elgranero.controller;

import co.elgranero.net.Product;
import java.io.IOException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import co.elgranero.controller.util.BDConnection;
import co.elgranero.net.Category;
import co.elgranero.net.Subcategory;
import co.elgranero.persistence.SqlInstructionsReader;
import co.elgranero.net.Presentation;
import co.elgranero.net.ProductPresentation;

public class ProductsManager {
    private SqlInstructionsReader sir;
    private Connection conn;

    public ProductsManager() throws IOException {
        this.sir = SqlInstructionsReader.getInstance();
        this.conn = BDConnection.getInstance().getConnection();
    }

    public boolean registCategory(String categoryName) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "CATEGORIAS_PRODUCTOS");
            pSt.setString(1, categoryName);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean registSubcategory(int categoryId, String subcategoryName) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "SUBCATEGORIAS_PRODUCTOS");
            pSt.setInt(1, categoryId);
            pSt.setString(2, subcategoryName);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean registProduct(int subcategoryId, String name, String description, Date expirationDate) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "PRODUCTOS");
            pSt.setInt(1, subcategoryId);
            pSt.setString(2, name);
            pSt.setString(3, description);
            pSt.setDate(4, expirationDate);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean registPresentation(String presentationName) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "PRESENTACIONES");
            pSt.setString(1, presentationName);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean registProductPresentation(int presentationId, int productId, double presentationPrice) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getInsertsQueryOf(conn, "PRESENTACIONES_PRODUCTOS");
            pSt.setInt(1, presentationId);
            pSt.setInt(2, productId);
            pSt.setDouble(3, presentationPrice);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public ArrayList<Category> obtainCategories() {
        ArrayList<Category> categories = new ArrayList<Category>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "CATEGORIAS_PRODUCTOS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                categories.add(new Category(
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_categoria")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return categories;
    }

    public ArrayList<Subcategory> obtainSubcategories() {
        ArrayList<Subcategory> subcategories = new ArrayList<Subcategory>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "SUBCATEGORIAS_PRODUCTOS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                subcategories.add(new Subcategory(
                        rs.getInt("id_subcategoria"),
                        rs.getInt("id_categoria"),
                        rs.getString("nombre_subcategoria"),
                        rs.getString("nombre_categoria")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return subcategories;
    }

    public ArrayList<Product> obtainProducts() {
        ArrayList<Product> products = new ArrayList<Product>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PRODUCTOS");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                products.add(new Product(
                        rs.getInt("id_producto"),
                        rs.getInt("id_subcategoria"),
                        rs.getString("nombre_producto"),
                        rs.getString("descripcion_producto"),
                        rs.getDate("fecha_vencimiento_producto"),
                        rs.getString("nombre_subcategoria"),
                        rs.getString("nombre_categoria")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return products;
    }

    public ArrayList<Presentation> obtainPresentations() {
        ArrayList<Presentation> presentations = new ArrayList<Presentation>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PRESENTACIONES");
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                presentations.add(new Presentation(
                        rs.getInt("id_presentacion"),
                        rs.getString("nombre_presentacion")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return presentations;
    }

    public ArrayList<ProductPresentation> obtainProductPresentations(int productId) {
        ArrayList<ProductPresentation> productPresentations = new ArrayList<ProductPresentation>();
        try {
            PreparedStatement pSt = sir.getConsultOf(conn, "PRESENTACIONES_PRODUCTOS");
            pSt.setInt(1, productId);
            ResultSet rs = pSt.executeQuery();
            while (rs.next()) {
                productPresentations.add(new ProductPresentation(
                        rs.getInt("id_presentacion"),
                        rs.getInt("id_producto"),
                        rs.getDouble("precio_presentacion"),
                        rs.getString("nombre_presentacion")));
            }
            rs.close();
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return productPresentations;
    }

    public boolean modifyCategory(Category category) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "CATEGORIAS_PRODUCTOS");
            pSt.setString(1, category.getName());
            pSt.setInt(2, category.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
            e.printStackTrace();
        }
        return result;
    }

    public boolean modifySubcategory(Subcategory subcategory) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "SUBCATEGORIAS_PRODUCTOS");
            pSt.setInt(1, subcategory.getIdCategory());
            pSt.setString(2, subcategory.getName());
            pSt.setInt(3, subcategory.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean modifyProduct(Product product) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "PRODUCTOS");
            pSt.setInt(1, product.getIdSubcategory());
            pSt.setString(2, product.getName());
            pSt.setString(3, product.getDescription());
            pSt.setDate(4, product.getExpirationDate());
            pSt.setInt(5, product.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean modifyPresentation(Presentation presentation) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "PRESENTACIONES");
            pSt.setString(1, presentation.getName());
            pSt.setInt(2, presentation.getId());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean modifyProductPresentation(ProductPresentation productPresentation) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getUpdateQueryOf(conn, "PRESENTACIONES_PRODUCTOS");
            pSt.setDouble(1, productPresentation.getPresentationPrice());
            pSt.setInt(2, productPresentation.getIdPresentation());
            pSt.setInt(3, productPresentation.getIdProduct());
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean deleteCategory(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "CATEGORIAS_PRODUCTOS");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean deleteSubcategory(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "SUBCATEGORIAS_PRODUCTOS");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean deleteProduct(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PRODUCTOS");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean deletePresentation(int id) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PRESENTACIONES");
            pSt.setInt(1, id);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }

    public boolean deleteProductPresentation(int presentationId, int productId) {
        boolean result = false;
        try {
            PreparedStatement pSt = sir.getDeleteQueryOf(conn, "PRESENTACIONES_PRODUCTOS");
            pSt.setInt(1, presentationId);
            pSt.setInt(2, productId);
            result = pSt.executeUpdate() == 1;
            pSt.close();
        } catch (SQLException | IOException e) {
        }
        return result;
    }
}