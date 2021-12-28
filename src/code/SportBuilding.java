package code;

public class SportBuilding {
    protected String name;
    protected String beginBuiltYear, endBuiltYear;
    protected Long totalFunding;
    protected String buildingType, typeOfBuild;

    public SportBuilding(String name, String beginBuiltYear, String endBuiltYear,
                         Long totalFunding, String buildingType, String typeOfBuild)
    {
        this.name = name;
        this.beginBuiltYear = beginBuiltYear.length() > 0 ? beginBuiltYear.split("\\.")[2] : "";
        this.endBuiltYear =  endBuiltYear.length() > 0 ? endBuiltYear.split("\\.")[2] : "";
        this.totalFunding = totalFunding;
        this.buildingType = buildingType;
        this.typeOfBuild = typeOfBuild;
    }
}