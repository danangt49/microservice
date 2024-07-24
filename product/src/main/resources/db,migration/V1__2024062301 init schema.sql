CREATE TABLE categories
(
    category_id   INT AUTO_INCREMENT NOT NULL,
    created_at    datetime           NOT NULL,
    updated_at    datetime           NOT NULL,
    category_name VARCHAR(255)       NOT NULL,
    CONSTRAINT pk_categories PRIMARY KEY (category_id)
);

CREATE TABLE products
(
    product_id    INT AUTO_INCREMENT NOT NULL,
    created_at    datetime           NOT NULL,
    updated_at    datetime           NOT NULL,
    product_name  VARCHAR(255)       NOT NULL,
    `description` VARCHAR(255)       NULL,
    price         DECIMAL            NOT NULL,
    image         VARCHAR(255)       NULL,
    stock         INT                NULL,
    category_id   INT                NULL,
    CONSTRAINT pk_products PRIMARY KEY (product_id)
);

ALTER TABLE products
    ADD CONSTRAINT FK_PRODUCTS_ON_CATEGORY FOREIGN KEY (category_id) REFERENCES categories (category_id);