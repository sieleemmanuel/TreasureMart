package com.sielehub.treasuremart

import com.sielehub.treasuremart.domain.model.Address
import com.sielehub.treasuremart.domain.model.Geolocation
import com.sielehub.treasuremart.domain.model.Name
import com.sielehub.treasuremart.domain.model.Product
import com.sielehub.treasuremart.domain.model.Rating
import com.sielehub.treasuremart.domain.model.SignupRequest
import com.sielehub.treasuremart.domain.model.SignupResponse
import com.sielehub.treasuremart.domain.model.User

val fakeNewUser = SignupRequest(
    email = "John@gmail.com",
    username = "johnd",
    password = "m38rmF$",
)
val fakeSignupResponse = SignupResponse(
    id = 1
)
val fakeUser = User(
    id = 1,
    email = "John@gmail.com",
    username = "johnd",
    password = "m38rmF$",
    name = Name(
        firstname = "John",
        lastname = "Doe"
    ),
    address = Address(
        city = "kilcoole",
        street = "7835 new road",
        number = 3,
        zipcode = "12926-3874",
        geolocation = Geolocation(
            lat = "-37.3159",
            long = "81.1496"
        )
    ),
    phone = "1-570-236-7033"
)

val fakeProducts = listOf(
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
        id = 2,
        title = "Mens Casual Premium Slim Fit T-Shirts ",
        price = 22.3,
        description = "Slim-fitting style, contrast raglan long sleeve, three-button henley placket, light weight & soft fabric for breathable and comfortable wearing. And Solid stitched shirts with round neck made for durability and a great fit for casual fashion wear and diehard baseball fans. The Henley style round neckline includes a three-button placket.",
        category = "men's clothing",
        image = "https://fakestoreapi.com/img/71-3HjGNDUL._AC_SY879._SX._UX._SY._UY_.jpg",
        rating = Rating(rate = 4.1, count = 259)
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
        id = 4,
        title = "Mens Casual Slim Fit",
        price = 15.99,
        description = "The color could be slightly different between on the screen and in practice. / Please note that body builds vary by person, therefore, detailed size information should be reviewed below on the product description.",
        category = "men's clothing",
        image = "https://fakestoreapi.com/img/71YXzeOuslL._AC_UY879_.jpg",
        rating = Rating(rate = 2.1, count = 430)
    ),
    Product(
        id = 5,
        title = "John Hardy Women's Legends Naga Gold & Silver Dragon Station Chain Bracelet",
        price = 695.0,
        description = "From our Legends Collection, the Naga was inspired by the mythical water dragon that protects the ocean's pearl. Wear facing inward to be bestowed with love and abundance, or outward for protection.",
        category = "jewelery",
        image = "https://fakestoreapi.com/img/71pWzhdJNwL._AC_UL640_QL65_ML3_.jpg",
        rating = Rating(rate = 4.6, count = 400)
    ),
    Product(
        id = 6,
        title = "Solid Gold Petite Micropave ",
        price = 168.0,
        description = "Satisfaction Guaranteed. Return or exchange any order within 30 days.Designed and sold by Hafeez Center in the United States. Satisfaction Guaranteed. Return or exchange any order within 30 days.",
        category = "jewelery",
        image = "https://fakestoreapi.com/img/61sbMiUnoGL._AC_UL640_QL65_ML3_.jpg",
        rating = Rating(rate = 3.9, count = 70)
    ),
    Product(
        id = 7,
        title = "White Gold Plated Princess",
        price = 9.99,
        description = "Classic Created Wedding Engagement Solitaire Diamond Promise Ring for Her. Gifts to spoil your love more for Engagement, Wedding, Anniversary, Valentine's Day...",
        category = "jewelery",
        image = "https://fakestoreapi.com/img/71YAIFU48IL._AC_UL640_QL65_ML3_.jpg",
        rating = Rating(rate = 3.0, count = 400)
    ),
    Product(
        id = 8,
        title = "Pierced Owl Rose Gold Plated Stainless Steel Double",
        price = 10.99,
        description = "Rose Gold Plated Double Flared Tunnel Plug Earrings. Made of 316L Stainless Steel",
        category = "jewelery",
        image = "https://fakestoreapi.com/img/51UDEzMJVpL._AC_UL640_QL65_ML3_.jpg",
        rating = Rating(rate = 1.9, count = 100)
    ),
    Product(
        id = 9,
        title = "WD 2TB Elements Portable External Hard Drive - USB 3.0 ",
        price = 64.0,
        description = "USB 3.0 and USB 2.0 Compatibility Fast data transfers Improve PC Performance High Capacity; Compatibility Formatted NTFS for Windows 10, Windows 8.1, Windows 7; Reformatting may be required for other operating systems; Compatibility may vary depending on user’s hardware configuration and operating system",
        category = "electronics",
        image = "https://fakestoreapi.com/img/61IBBVJvSDL._AC_SY879_.jpg",
        rating = Rating(rate = 3.3, count = 203)
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
        id = 16,
        title = "Lock and Love Women's Removable Hooded Faux Leather Moto Biker Jacket",
        price = 29.95,
        description = "100% POLYURETHANE(shell) 100% POLYESTER(lining) 75% POLYESTER 25% COTTON (SWEATER), Faux leather material for style and comfort / 2 pockets of front, 2-For-One Hooded denim style faux leather jacket, Button detail on waist / Detail stitching at sides, HAND WASH ONLY / DO NOT BLEACH / LINE DRY / DO NOT IRON",
        category = "women's clothing",
        image = "https://fakestoreapi.com/img/81XH0e8fefL._AC_UY879_.jpg",
        rating = Rating(rate = 2.9, count = 340),
    ),
    Product(
        id = 18,
        title = "MBJ Women's Solid Short Sleeve Boat Neck V ",
        price = 9.85,
        description = "95% RAYON 5% SPANDEX, Made in USA or Imported, Do Not Bleach, Lightweight fabric with great stretch for comfort, Ribbed on sleeves and neckline / Double stitching on bottom hem",
        category = "women's clothing",
        image = "https://fakestoreapi.com/img/71z3kpMAYsL._AC_UY879_.jpg",
        rating = Rating(rate = 4.7, count = 130)
    ),
    Product(
        id = 19,
        title = "Opna Women's Short Sleeve Moisture",
        price = 7.95,
        description = "100% Polyester, Machine wash, 100% cationic polyester interlock, Machine Wash & Pre Shrunk for a Great Fit, Lightweight, roomy and highly breathable with moisture wicking fabric which helps to keep moisture away, Soft Lightweight Fabric with comfortable V-neck collar and a slimmer fit, delivers a sleek, more feminine silhouette and Added Comfort",
        category = "women's clothing",
        image = "https://fakestoreapi.com/img/51eg55uWmdL._AC_UX679_.jpg",
        rating = Rating(rate = 4.5, count = 146)
    ),
)

const val fakeToken =
    "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJzdWIiOjEsInVzZXIiOiJqb2huZCIsImlhdCI6MTc0NDI2ODYxNX0.AvgFqI1b-rAAyS09NMNzcFxmNmPcjzxIo-PG509V1Yo"