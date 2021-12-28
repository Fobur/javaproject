package code;

import java.nio.file.Path;
import java.util.*;
import java.nio.file.Paths;

public class Parser {
    public static void parse(DataBase dataBase) {
        Path source = Paths.get("SportBuildings.csv");
        try {
            List<String> headers = new ArrayList<>();
            var scanner = new Scanner(source,"Windows-1251");
            Arrays.stream(scanner.nextLine().split("\","))
                    .forEach(x -> headers.add(x.replace("\"","").replace(":","")));
            List<String> neededHeadersWithIndex = new ArrayList<>();
            for (int i = 0; i < headers.size(); i++)
            {
                var x = headers.get(i);
                if (x.equals("Название") || x.equals("Действия с объектом")
                        || x.equals("Дата начала строительства / реконструкции")
                        || x.equals("Дата завершения строительства / реконструкции")
                        || x.equals("Общий объём финансирования")
                        || x.equals("Тип спортивного комплекса"))
                    neededHeadersWithIndex.add(String.format("%d,%s", i, x));
            }

            while (scanner.hasNext())
            {
                var str = scanner.nextLine();
                if(!scanner.hasNext())
                    break;
                var data = new ArrayList<String>();
                var isQuoteOpen = false;
                for (var i = 0; i < str.length();)
                {
                    isQuoteOpen = str.charAt(i) == '\"';
                    var endIndex = isQuoteOpen? i+1 :i;
                    while (endIndex < str.length())
                    {
                        if (str.charAt(endIndex) == ',') {
                            if (!isQuoteOpen){
                                break;
                            }
                        }
                        if (str.charAt(endIndex) == '\"' && isQuoteOpen)
                            isQuoteOpen = false;
                        endIndex++;
                    }
                    var a = str.substring(i, endIndex);
                    data.add(str.substring(i, endIndex));
                    i = endIndex + 1;
                }

                var name = "";
                var beginBuiltYear = "";
                var endBuiltYear = "";
                var buildingType = "";
                var typeOfBuild = " ";
                var totalFunding = 0L;
                for (var x : neededHeadersWithIndex)
                {
                    var index = Integer.valueOf(x.split(",")[0]);
                    var headerName = x.split(",")[1];
                    switch (headerName) {
                        case ("Название") -> name = data.get(index).replace("\"", "");
                        case ("Действия с объектом") -> typeOfBuild = data.get(index).replace("\"", "");
                        case ("Дата начала строительства / реконструкции") -> beginBuiltYear = data.get(index).replace("\"", "");
                        case ("Дата завершения строительства / реконструкции") -> endBuiltYear = data.get(index).replace("\"", "");
                        case ("Общий объём финансирования") -> totalFunding = Long.parseLong(data.get(index).replace("\"", ""));
                        case ("Тип спортивного комплекса") -> buildingType = data.get(index).replace("\"", "");
                        default -> throw new IllegalStateException("Unexpected value: " + headerName);
                    }
                }
                dataBase.insert(new SportBuilding(name, beginBuiltYear, endBuiltYear,
                        totalFunding, buildingType, typeOfBuild));
            }
        } catch (Exception e)
        {
            System.out.println(e);
        }
    }
}
