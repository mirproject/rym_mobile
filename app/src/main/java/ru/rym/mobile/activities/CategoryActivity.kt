package ru.rym.mobile.activities

import android.graphics.Color
import android.os.Bundle
import android.view.View;
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import ru.rym.mobile.R
import ru.rym.mobile.entities.CategoryEnum
import ru.rym.mobile.entities.dto.SelectedCategoriesDTO.categories
import ru.rym.mobile.entities.dto.SelectedCategoriesDTO.dislikeCategories
import ru.rym.mobile.entities.dto.SelectedCategoriesDTO.likeCategories
import ru.rym.mobile.databinding.ActivityCategoryBinding

class CategoryActivity : AppCompatActivity() {

    private lateinit var activityCategoryBinding: ActivityCategoryBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityCategoryBinding = ActivityCategoryBinding.inflate(layoutInflater)
        setContentView(activityCategoryBinding.root)

        activityCategoryBinding.categoryRelaxButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_relax_tv), CategoryEnum.RELAX)
        })

        activityCategoryBinding.categoryBusinessButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_business_tv), CategoryEnum.BUSINESS)
        })

        activityCategoryBinding.categoryCultureButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_culture_tv), CategoryEnum.CULTURE)
        })

        activityCategoryBinding.categoryScienceButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_science_tv), CategoryEnum.SCIENCE)
        })

        activityCategoryBinding.categoryNewsButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_news_tv), CategoryEnum.NEWS)
        })

        activityCategoryBinding.categoryArchitectureButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_architecture_tv), CategoryEnum.ARCHITECTURE)
        })

        activityCategoryBinding.categoryCarsButton.setOnClickListener(View.OnClickListener {
            changeCategory(findViewById(R.id.category_cars_tv), CategoryEnum.CARS)
        })

    }

    private fun setCategoryToView(categoryTextView: TextView, category: CategoryEnum) {

        if (likeCategories.contains(category)) {
            categoryTextView.setTextColor(Color.GREEN)
        } else if (dislikeCategories.contains(category)) {
            categoryTextView.setTextColor(Color.RED)
        } else {
            categoryTextView.setTextColor(Color.parseColor("#757575"))
        }
    }

    private fun changeCategory(categoryTextView: TextView, category: CategoryEnum) {
        changeCategory(category)
        setCategoryToView(categoryTextView, category)
    }

    private fun changeCategory(category: CategoryEnum) {
        if (dislikeCategories.contains(category)) {
            dislikeCategories.remove(category)
            categories.add(category)
        } else if (categories.contains(category)) {
            categories.remove(category)
            likeCategories.add(category)
        } else {
            likeCategories.remove(category)
            dislikeCategories.add(category)
        }
    }
}