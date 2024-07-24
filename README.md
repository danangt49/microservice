# Arsitektur Microservice

Proyek ini mendemonstrasikan arsitektur microservice yang terdiri dari dua layanan: **Layanan Produk** dan **Layanan Pesanan**. Setiap layanan dibangun menggunakan **Spring Boot** versi **3.3.2** dan **Java 17**, memanfaatkan kemampuan pengembangan Java modern dan keandalan kerangka kerja Spring.

## Gambaran Umum Layanan

### Product Service

Layanan Produk bertanggung jawab untuk mengelola data dan operasi yang berkaitan dengan produk. Layanan ini menyediakan API untuk membuat, memperbarui, dan mengambil informasi produk, memastikan pengelolaan katalog produk yang efisien. Layanan ini terhubung ke basis data **MySQL** untuk penyimpanan data produk yang persisten.

### Order Service

Layanan Pesanan menangani pemrosesan dan pengelolaan pesanan. Layanan ini menyediakan endpoint untuk membuat, memperbarui, dan melacak pesanan, serta berintegrasi dengan Layanan Produk untuk memastikan manajemen stok yang akurat. Data pesanan juga disimpan di basis data **MySQL**, menjaga catatan pesanan yang andal.

## Teknologi yang Digunakan

- **Spring Boot 3.3.2**: Menyediakan kerangka kerja yang kuat untuk membangun microservice yang skalabel dan mudah dirawat dengan konfigurasi minimal.
- **Java 17**: Versi rilis jangka panjang terbaru dari Java, menawarkan fitur bahasa modern dan peningkatan.
- **MySQL**: Sistem manajemen basis data relasional yang digunakan untuk menyimpan dan mengelola data untuk Layanan Produk dan Pesanan.
- **Zipkin**: Sistem tracing terdistribusi yang digunakan untuk mengumpulkan data waktu yang diperlukan untuk memecahkan masalah latensi dalam arsitektur microservice.
- **Kafka**: Platform streaming peristiwa terdistribusi yang digunakan untuk membangun pipeline data real-time dan aplikasi streaming, memungkinkan komunikasi antara Layanan Produk dan Pesanan.

## Komunikasi Antar Microservice

Layanan saling berkomunikasi melalui API RESTful dan memanfaatkan **Apache Kafka** untuk pesan asinkron. Pendekatan ini memastikan komunikasi antar layanan yang terpisah, skalabel, dan tangguh. Selain itu, **Zipkin** diintegrasikan ke dalam sistem untuk menyediakan tracing terdistribusi, memungkinkan visibilitas dan pemecahan masalah yang lebih baik dari alur permintaan di seluruh microservice.

## Pengaturan dan Penerapan

Untuk menerapkan microservice, Docker Compose digunakan untuk mengelola tumpukan aplikasi, termasuk Layanan Produk dan Pesanan,
