

package vn.tdc.edu.fooddelivery.models;

public class CategoryModel_Test extends BaseModel {
    private int idCategory;
    private String name;
    private int image;


    public CategoryModel_Test(int id ,String name, int image) {
        this.idCategory = id;
        this.name = name;
        this.image = image;
    }

    public int getIdCategory() {
        return idCategory;
    }

    public void setIdCategory(int idCategory) {
        this.idCategory = idCategory;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    @Override
    public String toString() {
        return "CategoryModel_Test{" +
                "id=" + idCategory +
                ", name='" + name + '\'' +
                ", image=" + image +
                '}';
    }
}
