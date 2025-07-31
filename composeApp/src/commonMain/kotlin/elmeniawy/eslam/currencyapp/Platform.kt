package elmeniawy.eslam.currencyapp

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform