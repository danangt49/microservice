INSERT INTO categories (created_at, updated_at, category_name)
VALUES
    ('2024-07-23 10:00:00', '2024-07-23 10:00:00', 'Electronics'),
    ('2024-07-23 10:15:00', '2024-07-23 10:15:00', 'Clothing'),
    ('2024-07-23 10:30:00', '2024-07-23 10:30:00', 'Home Appliances'),
    ('2024-07-23 10:45:00', '2024-07-23 10:45:00', 'Books'),
    ('2024-07-23 11:00:00', '2024-07-23 11:00:00', 'Toys');


INSERT INTO products (created_at, updated_at, product_name, description, price, image, category_id, stock)
VALUES
    ('2024-07-23 11:30:00', '2024-07-23 11:30:00', 'Smartphone', 'Latest model with advanced features', 10499850, 'https://unsplash.com/photos/black-android-smartphone-displaying-blue-and-red-apple-logo-C4wFBBXRyyE/', 1, 10),
    ('2024-07-23 11:45:00', '2024-07-23 11:45:00', 'T-shirt', '100% cotton t-shirt in various colors', 299850, 'https://unsplash.com/photos/a-pair-of-t-shirts-sitting-on-top-of-a-wooden-chair-DvIMVdLQinU', 2, 10),
    ('2024-07-23 12:00:00', '2024-07-23 12:00:00', 'Blender', 'High-speed blender for smoothies', 1349850, 'https://unsplash.com/photos/a-person-pouring-a-green-liquid-into-a-blender-RT1aCnWmP5c', 3, 5),
    ('2024-07-23 12:15:00', '2024-07-23 12:15:00', 'Novel', 'Best-selling fiction novel', 224850, 'https://unsplash.com/photos/a-stack-of-three-books-sitting-on-top-of-each-other-LxfdeRS3G6o', 4, 5),
    ('2024-07-23 12:30:00', '2024-07-23 12:30:00', 'Action Figure', 'Collectible action figure with accessories', 374850, 'https://unsplash.com/photos/yellow-and-red-lego-minifig-lQvr3z9cGYE', 5, 2);
