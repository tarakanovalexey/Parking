package com.company.entities;

public class Car {
    private String licenseplate;

    public Car() {
        int a = 0;
        int b = 9;
        String plate = ""+(int) (Math.random() * b)+(int) (Math.random() * b)+(int) (Math.random() * b);
        String[] letters = new String[]{"À","Â","Å","Ê","Ì","Í","Î","Ð","Ñ","Ò","Ó","Õ"};
        b = letters.length-1;
        String letter1 = letters[a + (int) (Math.random() * b)];
        String letter2 = letters[a + (int) (Math.random() * b)];
        String letter3 = letters[a + (int) (Math.random() * b)];
        plate = letter1+plate+letter2+letter3;
        String[] region = new String[]{"77","97","99","177","197","199","777",
                "799","01","04","09","10","13","133","16","116","19","22","25","125","02","102","05","08","11","14","17","20","95","23","93","123","26","03",
                "06","09","12","15","18","21","121","24","84","88","124","27","28","31","34","37","40","43","46","49","52","152","29","32","35","38","85","41","82",
                "44","47","50","90","150","190","53","30","33","36","136","39","91","42","142","45","48","51","54","154","55","58","61","161","64","164"
                ,"67","70","73","173","76","79","87","56","59","81","159","62","65","68","71","74","174","83","89","57","60","63","163","66","96","69","72"
                ,"75","80","78","98","178","86"};
        b = region.length-1;
        String regnum = region[a + (int) (Math.random() * b)];
        plate += regnum;
        licenseplate = plate;
    }

    public String getLicenseplate() {
        return licenseplate;
    }
}
