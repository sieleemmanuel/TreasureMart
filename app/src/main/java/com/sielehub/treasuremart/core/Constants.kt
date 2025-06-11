package com.sielehub.treasuremart.core

import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Cart
import com.sielehub.treasuremart.domain.model.CartProduct
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.domain.model.Order
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.Rating
import java.time.LocalDate
import java.util.Date

class Constants {
    object HttpRoutes {
        //private const val BASE_URL ="https://api.escuelajs.co/api/v1" without cart
        private const val BASE_URL = "https://fakestoreapi.com"
        const val PRODUCTS_ENDPOINT = "$BASE_URL/products"
        const val PRODUCT_ENDPOINT = "$BASE_URL/products/"
        const val CATEGORY_PRODUCTS_ENDPOINT = "$BASE_URL/products/category/"
        const val CATEGORIES_ENDPOINT = "$BASE_URL/categories"
        const val CARTS_ENDPOINT = "$BASE_URL/carts"
        const val USERS_ENDPOINT = "$BASE_URL/users"
        const val CREATE_USER_ENDPOINT = "$BASE_URL/users/"
        const val AUTH_ENDPOINT = "$BASE_URL/auth/login/"
    }

    object AppThemes {
        const val LIGHT = "Light mode"
        const val DARK = "Dark mode"
        const val SYSTEM = "System default"
    }

    object OrderStatus {
        const val TO_PAY = "To Pay"
        const val TO_SHIP = "To Ship"
        const val SHIPPED = "Shipped"
        const val COMPLETED = "Completed"
        const val RETURNED = "Returned"
    }

    object PaymentMethod {
        const val VISA = "VISA"
        const val GOOGLE_PAY = "Google Pay"
        const val CASH_ON_DELIVERY = "Cash on Delivery"
    }

    companion object {
        const val PARAM_PRODUCT_ID = "productId"
        const val PARAM_PRODUCT_CATEGORY = "productCategory"
        const val PARAM_SEARCH_QUERY = "searchQuery"
        fun products() = listOf(
            Product(
                id = 1,
                title = "Fjallraven - Foldsack No. 1 Backpack, Fits 15 Laptops",
                price = 109.95,
                description = "Your perfect pack for everyday use and walks in the forest. Stash your laptop (up to 15 inches) in the padded sleeve, your everyday",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/81fPKd-2AYL._AC_SL1500_.jpg",
                rating = Rating(rate = 3.9, count = 120)
            ),
            Product(
                id = 3,
                title = "Mens Cotton Jacket",
                price = 55.99,
                description = "great outerwear jackets for Spring/Autumn/Winter, suitable for many occasions, such as working, hiking, camping, mountain/rock climbing, cycling, traveling or other outdoors. Good gift choice for you or your family member. A warm hearted love to Father, husband or son in this thanksgiving or Christmas Day.",
                category = "men's clothing",
                image = "https://fakestoreapi.com/img/71li-ujtlUL._AC_UX679_.jpg",
                rating = Rating(rate = 4.7, count = 500)
            ),
            Product(
                id = 10,
                title = "SanDisk SSD PLUS 1TB Internal SSD - SATA III 6 Gb/s",
                price = 109.0,
                description = "Easy upgrade for faster boot up, shutdown, application load and response (As compared to 5400 RPM SATA 2.5” hard drive; Based on published specifications and internal benchmarking tests using PCMark vantage scores) Boosts burst write performance, making it ideal for typical PC workloads The perfect balance of performance and reliability Read/write speeds of up to 535MB/s/450MB/s (Based on internal testing; Performance may vary depending upon drive capacity, host device, OS and application.)",
                category = "electronics",
                image = "https://fakestoreapi.com/img/61U7T1koQqL._AC_SX679_.jpg",
                rating = Rating(rate = 2.9, count = 470)
            ),
            Product(
                id = 11,
                title = "Silicon Power 256GB SSD 3D NAND A55 SLC Cache Performance Boost SATA III 2.5",
                price = 109.0,
                description = "3D NAND flash are applied to deliver high transfer speeds Remarkable transfer speeds that enable faster bootup and improved overall system performance. The advanced SLC Cache Technology allows performance boost and longer lifespan 7mm slim design suitable for Ultrabooks and Ultra-slim notebooks. Supports TRIM command, Garbage Collection technology, RAID, and ECC (Error Checking & Correction) to provide the optimized performance and enhanced reliability.",
                category = "electronics",
                image = "https://fakestoreapi.com/img/71kWymZ+c+L._AC_SX679_.jpg",
                rating = Rating(rate = 4.8, count = 319)
            ),
            Product(
                id = 13,
                title = "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",
                price = 599.0,
                description = "21. 5 inches Full HD (1920 x 1080) widescreen IPS display And Radeon free Sync technology. No compatibility for VESA Mount Refresh Rate: 75Hz - Using HDMI port Zero-frame design | ultra-thin | 4ms response time | IPS panel Aspect ratio - 16: 9. Color Supported - 16. 7 million colors. Brightness - 250 nit Tilt angle -5 degree to 15 degree. Horizontal viewing angle-178 degree. Vertical viewing angle-178 degree 75 hertz",
                category = "electronics",
                image = "https://fakestoreapi.com/img/81QpkIctqPL._AC_SX679_.jpg",
                rating = Rating(rate = 2.9, count = 250)
            )
        )

        val wishList = products().shuffled().toMutableList()

        fun categories() = listOf(
            "electronics",
            "jewelery",
            "men's clothing",
            "women's clothing"
        )

        fun cartProducts() = listOf(
            CartProduct(
                productId = 13,
                quantity = 2,
                title = "Acer SB220Q bi 21.5 inches Full HD (1920 x 1080) IPS Ultra-Thin",
                price = 599.0,
                description = "21. 5 inches Full HD (1920 x 1080) widescreen IPS display And Radeon free Sync technology. No compatibility for VESA Mount Refresh Rate: 75Hz - Using HDMI port Zero-frame design | ultra-thin | 4ms response time | IPS panel Aspect ratio - 16: 9. Color Supported - 16. 7 million colors. Brightness - 250 nit Tilt angle -5 degree to 15 degree. Horizontal viewing angle-178 degree. Vertical viewing angle-178 degree 75 hertz",
                category = "electronics",
                image = "https://fakestoreapi.com/img/81QpkIctqPL._AC_SX679_.jpg"
            ),
            CartProduct(1, 1),
            CartProduct(8, 4),
            CartProduct(10, 1)
        )

        val order = Order(
            orderId = 179090630039539L,
            address = Address(
                city = "Pretoria",
                number = 123456789,
                street = "Address 1",
                geolocation = Geolocation("123.456", "789.012"),
                zipcode = "12345",
                shippingFee = 258.00,
                isDefault = true
            ),
            orderItems = cartProducts().take(2),
            orderTotal = 1235.00,
            orderDate = LocalDate.now().toString(),
            orderStatus = OrderStatus.SHIPPED,
            paymentMethod = PaymentMethod.VISA
        )

        fun myCart() = Cart(Date().toString(), 1, cartProducts(), 1)

    }
}