package domain.usecase

interface UseCase<I, O> {
    operator fun invoke(input: I): O
}