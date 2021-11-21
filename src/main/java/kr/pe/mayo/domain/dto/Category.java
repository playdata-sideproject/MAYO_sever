package kr.pe.mayo.domain.dto;

public enum Category {
    FURNITURE("가구"),
    CERAMICS("도예"),
    METALWORK("금속공예"),
    ILLUSTRATION("일러스트");

    private String category;

    Category(String category) {
        this.category = category;
    }

    public String getCategory() {
        return category;
    }
}
