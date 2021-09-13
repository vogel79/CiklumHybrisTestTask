package domain;

import java.sql.Date;

public class Products {
    private int id;
    private String name;
    private int price;
    private ProductsStatus status;
    private Date created_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public ProductsStatus getStatus() {
        return status;
    }

    public void setStatus(ProductsStatus status) {
        this.status = status;
    }

    public Date getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Date created_at) {
        this.created_at = created_at;
    }

    @Override
    public String toString() {
        return "Products{" +
            "id=" + id +
            ", name='" + name + '\'' +
            ", price=" + price +
            ", status=" + status +
            ", created_at=" + created_at +
            '}';
    }
}
