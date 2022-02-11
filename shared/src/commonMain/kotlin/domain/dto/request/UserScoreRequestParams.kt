package domain.dto.request

class UserScoreRequestParams(
    val userId : String,
    val type: Type = Type.To,
) {

    enum class Type(val value: String) {
        To("to"),
        From("from"),
    }

}