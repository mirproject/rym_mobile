package ru.rym.mobile.entities

data class Coordinates(
    val latitude: Double,
    val longitude: Double
) {
    companion object {
        val ZERO = Coordinates(0.0, 0.0)
        val ONE = Coordinates(1.0, 1.0)

        /**
         * Get coordinates value from string.
         * @param coordinates coordinates string in 'Double(lat),Double(lon)' format
         *  example: '51.522192,39.522743' - lat, lon format
         * @return Coordinate object
         * @throws IllegalArgumentException
         */
        fun valueOf(coordinates: String): Coordinates {
            val coordinateValues = coordinates.split(",")
            if (coordinateValues.size != 2)
                throw IllegalArgumentException("Wrong coordinates string: $coordinates, expect f.e: '51.522192,39.522743'")

            try {
                return Coordinates(coordinateValues.first().toDouble(), coordinateValues.last().toDouble())
            } catch (e: NumberFormatException) {
                throw IllegalArgumentException(
                    "Wrong coordinates string: $coordinates, expect f.e: '51.522192,39.522743'",
                    e
                )
            }
        }
    }

    fun toCoordinatesName(): String {
        return "${this.latitude},${this.longitude}"
    }
}