package ru.rym.mobile.entities.dto

import ru.rym.mobile.entities.CategoryEnum

/**
 *
 * Выбранные категории
 * @date 07.07.2022
 * @author skyhunter
 *
 */
object SelectedCategoriesDTO {

    val likeCategories: MutableList<CategoryEnum> = emptyList<CategoryEnum>().toMutableList()
    val categories: MutableList<CategoryEnum> = CategoryEnum.values().toMutableList()
    val dislikeCategories: MutableList<CategoryEnum> = emptyList<CategoryEnum>().toMutableList()

}