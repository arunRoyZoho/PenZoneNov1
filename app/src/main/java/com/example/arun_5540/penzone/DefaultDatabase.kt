package com.example.arun_5540.penzone


class DefaultDatabase(){





    fun updateProducts():MutableList<Product>{
        val listOfProducts = mutableListOf<Product>()
        addProductList(listOfProducts)
        return listOfProducts
    }


    fun addUser(): MutableList<User>{
        val user1 = User(name = "frequent user" , phoneNumber = "9988776655", email = "frequentShopper@gmail.com", password = "password")
        val user2 = User(name = "window shopper", phoneNumber = "9876543210", email = "windowShopper@gmail.com"  , password = "password")
        val user3 = User(name = "Surendar"      , phoneNumber = "8714281098", email = "surendar@gmail.com"       , password = "1234")

        val userList = mutableListOf<User>()
        userList.add(user1)
        userList.add(user2)
        userList.add(user3)
        return userList
    }


    private fun addProductList(productList: MutableList<Product>){

        val product1  = Product(id = 1,  name = "gripper"              ,brand ="cello" ,          colour = "blue",  price = 10f,   stockLeft = 465,  description = "Cello Gripper Ballpoint Pen Blue: Every Cello pen Undergoes rigorous quality testing under extreme condition-ink, tip, ball and body are all guaranteed 100% quality tested to provide you a superlative writing experience.")
        val product2  = Product(id = 2,  name = "Jotter",               brand = "Parker",         colour = "black", price = 185f,  stockLeft =  56,  description = "Exquisite stainless steel body for extra durability with Chrome plated clip lends elegance. Button action fitted with standard ball point refill. Clip material: Stainless Steel.")
        val product3  = Product(id = 3,  name = "Bullet 375Gcl",        brand ="Fisher Space" ,   colour = "blue",  price = 2850f, stockLeft = 765,  description = "Bulletthe fisher space bullet pen writes at any angle, upside down, underwater, and in harsh temperature conditions (hot and cold). When closed, this pen slightly resembles a long bullet and it is this design that has been exhibited for a number of years by new york's museum of modern art as an outstanding example of contemporary industrial design. When the bullet space pen is open, it measures 5. 3 inches, a closed bullet pen measures 3. 7 inches. The compact shape let you carry it anywhere and everywhere. This collection comes in a variety of finish giving you a wide range to choose from.")
        val product4  = Product(id = 4,  name = "Pen Spr1F Ball Point" ,brand ="Fisher Space" ,   colour = "black", price = 480f,  stockLeft =  67,  description = "Every Fisher pen Undergoes rigorous quality testing under extreme condition-ink, tip, ball and body are all guaranteed 100% quality tested to provide you a superlative writing experience.")
        val product5  = Product(id = 5,  name = "Mini Ballpoint T-3",   brand = "Zebra",          colour = "blue",  price = 500f,  stockLeft =   2,  description = "High quality and popular Japan are selected carefully and it sells with reasonable set pricing. ")
        val product6  = Product(id = 6,  name = "Baoer Emotion",        brand = "Neo Gold Leaf",  colour = "blue",  price = 230f,  stockLeft =  12,  description = "Neo Gold Leaf Neo Gold Baoer Emotion Fountain Pen (Black)")
        val product7  = Product(id = 7,  name = "Jiffy Blue",           brand ="reynolds" ,       colour = "black", price  = 25f,  stockLeft =  34,  description = "The Speedy Gel Pen")
        val product8  = Product(id = 8,  name = "Fine Grip",            brand ="cello" ,          colour = "blue",  price = 40f,   stockLeft = 545,  description = "Cello Fingergrip Ball Pen Black: Every Cello pen Undergoes rigorous quality testing under extreme condition-ink, tip, ball and body are all guaranteed 100% quality tested to provide you a superlative writing experience.")
        val product9  = Product(id = 9,  name = "Classic Matte ",       brand = "Parker",         colour = "black", price = 45f,   stockLeft =  67,  description = "Matte Eposy resin coated stainless steel cap and barrel for premium looks with contrasting chrome plated stainless steel clip. Button action fitted with ball point refill. Clip material: Stainless Steel.")
        val product10 = Product(id = 10, name = "V7 Hi-tecpoint rT",    brand = "Pilot",          colour = "blue",  price = 135f,  stockLeft =  88,  description = "Refillable product through cartridge. Unique ‘3-Dimple Tip Technology’ from Japan for precision writing. Pure liquid ink for smooth Skip free writing. Patented ATT System to ensure instant start every time. See through ink reservoir which indicates remaining ink level.Fine tip of .7 mm which delivers .4mm width of stroke.")
        val product11 = Product(id = 11, name = "CWM 301",              brand = "Cruiser",        colour = "black", price = 8500f, stockLeft = 567,  description ="This supremely polished, multilayered, brilliant black-lacquer-finish ball pen is the epitome of elegance and style. If class is what appeals to you the most, this pen is calling at you. The sterling silver 925, the solid spring supported clip, the pinstripe pattern, and the easy twist action mechanism gives this pen that edge you are looking for." )
        val product12 = Product(id = 12, name = "849 Fluorescent",      brand = "Caran D'Ache",   colour = "blue" , price = 2100f, stockLeft =  34,  description = "849simultaneously contemporary, fun and casual. An aluminium hexagonal silhouette with a trendy graphic design. The 849 represents the emblematic ballpoint pen of caran d'ache's office range, suited to all occasions. It is accompanied by an ultra-flat case, ideal for carrying around when on the move or if you are looking for an original gift idea.")
        val product13 = Product(id = 13, name = "Hi tecpoint V10",      brand = "Pilot",          colour = "blue",  price = 25f ,  stockLeft =  54,  description ="Product description of Pilot Hi tecpoint V7 Cartridge-Blue Ink Color -Blue Set Contents- 2 Pcs Features - V7 Cartridge System Eco Friendly Product - Yes (71.3% recycled material) Ink type - Pure Liquid Ink" )
        val product14 = Product(id = 14, name = "Franklin Covey ",      brand = "Cross",          colour ="blue" ,  price = 795f,  stockLeft =  45,  description = "The Freemont pen and pencil deliver traditional styling with a contemporary flair. Every FranklinCovey writing instrument is accompanied by a lifetime mechanical guarantee, assuring many years of writing pleasure.")
        val product15 = Product(id = 15, name = "Calais Chrome",        brand = "Cross",          colour = "blue",  price = 1410f, stockLeft =   3,  description ="With Art Deco inspired lines and striking two-toned finish, the Calais Collection is an instant classic. Lightweight, sleek, with chrome accents and a bold profile, it brings a crisp, clean look to the table every time." )
        val product16 = Product(id = 16, name = "Classic Century",      brand = "Cross",          colour = "blue",  price = 1279f, stockLeft =  32,  description ="Authenticate all your important documents with elegance. Sign with the branded pens that match to your persona well. These can also be gifted to a dear one." )
        val product17 = Product(id = 17, name = "Classic Century 3502", brand = "Cross",          colour = "black", price = 1490f, stockLeft =  21,  description ="For those that want to have a little fun while getting down to business, this capless rollerball pen combines performance and practicality with play-ability. Just slide it open, unleash your thoughts, and then snap it shut. Slide open. Snap shut. Slide open. Snap shut. You won't be able to resist the temptation to play with the new Cross Edge." )
        val product18 = Product(id = 18, name = "Edge Roller",          brand = "Cross",          colour = "blue",  price = 1850f, stockLeft = 234,  description = "The A. T. Cross Company was established in 1846 as the first American manufacturer of fine writing instruments. They originally crafted elegantly tooled gold and silver casings for wooden pencils and introduced many important firsts in the field of writing instruments including the precursor of the ball-point pen and the first mechanical pencil. A. T. Cross Company is listed on the Nasdaq Stock Market LLC & Acirc & registered under the symbol ATX.")
        val product19 = Product(id = 19, name = "Click",                brand = "Cross",          colour = "blue",  price = 1550f, stockLeft =  32,  description = "New Cross Click series is the choice of movers, shakers and ground breakers since 1946. The pen features a lacquer barrel with a quick, retractable click. The Cross Click offers the same smooth lines and slender grip as the Classic Century without the twist. Just click to express yourself and enjoy superior writing experience with specially formulated ink that flows flawlessly. It is a modern design update to a classic icon." )
        val product20 = Product(id = 20, name = " Contemporary Pen",    brand = "LAPIS BARD",     colour = "blue",  price = 6500f ,stockLeft =  54,  description = "Crafted for gentlemen who don_t follow trends, but announce them, the Contemporary Dark Metal ballpoint pen is nothing short of a modern masterpiece. Balanced, light-bodied and crowned with a sharp-faceted clip, it exemplifies English design and craftsmanship. The premium leather card holder beautifully complements this remarkable fine writing instrument. It comes equipped with six credit card slots, an ID window plus two slide-in pockets that can be used to store folded bills or additional cards. Its simplicity leaves a lasting impression.")


        productList.add(product1)
        productList.add(product2)
        productList.add(product3)
        productList.add(product4)
        productList.add(product5)
        productList.add(product6)
        productList.add(product7)
        productList.add(product8)
        productList.add(product9)
        productList.add(product10)
        productList.add(product11)
        productList.add(product12)
        productList.add(product13)
        productList.add(product14)
        productList.add(product15)
        productList.add(product16)
        productList.add(product17)
        productList.add(product18)
        productList.add(product19)
        productList.add(product20)


    }
}
