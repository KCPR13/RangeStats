package pl.kacper.misterski.rangestats.core.domain.enums

enum class Caliber(val label: String, val applicableTo: Set<WeaponType>) {
    // Rifle
    REMINGTON_223(".223 Remington / 5.56x45mm NATO ", setOf(WeaponType.RIFLE)),
    SOVIET_7_62X39("7.62x39mm", setOf(WeaponType.RIFLE)),
    WIN_308(".308 Winchester / 7.62x51mm NATO", setOf(WeaponType.RIFLE)),
    R_7_62X54("7.62x54mmR", setOf(WeaponType.RIFLE)),
    BLACKOUT_300(".300 AAC Blackout", setOf(WeaponType.RIFLE)),
    CREEDMOOR_6_5("6.5 Creedmoor", setOf(WeaponType.RIFLE)),
    SPRINGFIELD_30_06(".30-06 Springfield", setOf(WeaponType.RIFLE)),
    WIN_270(".270 Winchester", setOf(WeaponType.RIFLE)),
    SPC_6_8("6.8 SPC", setOf(WeaponType.RIFLE)),
    WIN_243(".243 Winchester", setOf(WeaponType.RIFLE)),

    // Pistol
    LUGER_9MM("9mm Luger", setOf(WeaponType.PISTOL, WeaponType.RIFLE)),
    MAKAROV_9X18("9x18 Makarov", setOf(WeaponType.PISTOL)),
    ACP_380(".380 ACP", setOf(WeaponType.PISTOL)),
    SW_40(".40 S&W", setOf(WeaponType.PISTOL, WeaponType.RIFLE)),
    AUTO_10MM("10mm Auto", setOf(WeaponType.PISTOL, WeaponType.RIFLE)),
    ACP_45(".45 ACP", setOf(WeaponType.PISTOL, WeaponType.RIFLE)),
    ACP_32(".32 ACP", setOf(WeaponType.PISTOL)),
    ACP_25(".25 ACP", setOf(WeaponType.PISTOL)),
    REM_5_7X28("5.7x28mm", setOf(WeaponType.PISTOL)),
    SUPER_38(".38 Super", setOf(WeaponType.PISTOL)),
    SIG_357(".357 SIG", setOf(WeaponType.PISTOL)),

    // Revolver
    SPECIAL_38(".38 Special", setOf(WeaponType.REVOLVER)),
    MAGNUM_357(".357 Magnum", setOf(WeaponType.REVOLVER)),
    SPECIAL_44(".44 Special", setOf(WeaponType.REVOLVER)),
    MAGNUM_44(".44 Magnum", setOf(WeaponType.REVOLVER)),
    COLT_45_LC(".45 Colt (Long Colt)", setOf(WeaponType.REVOLVER)),
    SW_LONG_32(".32 S&W Long", setOf(WeaponType.REVOLVER)),
    HR_MAGNUM_32(".32 H&R Magnum", setOf(WeaponType.REVOLVER)),
    FEDERAL_MAGNUM_327(".327 Federal Magnum", setOf(WeaponType.REVOLVER)),
    MAGNUM_41(".41 Magnum", setOf(WeaponType.REVOLVER)),
    CASULL_454(".454 Casull", setOf(WeaponType.REVOLVER)),
    RUGER_480(".480 Ruger", setOf(WeaponType.REVOLVER)),
    SW_MAGNUM_460(".460 S&W Magnum", setOf(WeaponType.REVOLVER)),
    SW_MAGNUM_500(".500 S&W Magnum", setOf(WeaponType.REVOLVER)),
    WMR_22(".22 WMR", setOf(WeaponType.REVOLVER)),

    // Universal
    LR_22(".22 LR", setOf(WeaponType.PISTOL, WeaponType.REVOLVER, WeaponType.RIFLE)),
    ;
}