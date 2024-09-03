package com.example.newsapp.data.local

import androidx.room.TypeConverter
import com.example.newsapp.common.Constants
import com.example.newsapp.data.model.Source

class SourceConverter {

    @TypeConverter
    fun fromSource(source: Source): String {
        return "${source.id},${source.name}"
    }

    @TypeConverter
    fun toSource(sourceString: String): Source {
        val parts = sourceString.split(",")
        return Source(parts[0], parts[1])
    }
}

class CategoryConverter {

    @TypeConverter
    fun fromCategory(category: Constants.CATEGORY): String {
        return category.value
    }

    @TypeConverter
    fun toCategory(categoryString: String): Constants.CATEGORY {
        Constants.CATEGORY.entries.forEach {
            if (it.value == categoryString) return it
        }
        return Constants.CATEGORY.GENERAL
    }
}

