package domain.dto.request

class ScoreboardRequestParams(
    val type: Type = Type.To,
    val duration: Duration = Duration.Monthly,
) {

    enum class Type(val value: String) {
        To("to"),
        From("from"),
    }

    enum class Duration(val value: String) {
        Monthly("monthly"),
        Weekly("weekly"),
        All("all"),
    }

}