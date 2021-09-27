package com.appsnipp.claraerp.saibookhouse.Model;

import java.util.List;

public class PackageInfooModel {
    String Name;
    List<Data> data;
    List<Title> Titles;

    public PackageInfooModel(String name, List<Data> data, List<Title> titles) {
        Name = name;
        this.data = data;
        Titles = titles;
    }

    public List<Title> getTitles() {
        return Titles;
    }

    public void setTitles(List<Title> titles) {
        Titles = titles;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        Name = name;
    }

    public List<Data> getData() {
        return data;
    }

    public void setData(List<Data> data) {
        this.data = data;
    }

    public static class Data{
        String Type,HsnCode,Price,Quantity,TotalAmount,Title;

        public Data(String title,String price, String quantity, String totalAmount) {
            Price = price;
            Quantity = quantity;
            TotalAmount = totalAmount;
            Title = title;
        }

        public Data(String type, String hsnCode, String price, String quantity, String totalAmount) {
            Type = type;
            HsnCode = hsnCode;
            Price = price;
            Quantity = quantity;
            TotalAmount = totalAmount;
        }

        public String getType() {
            return Type;
        }

        public void setType(String type) {
            Type = type;
        }

        public String getHsnCode() {
            return HsnCode;
        }

        public void setHsnCode(String hsnCode) {
            HsnCode = hsnCode;
        }

        public String getPrice() {
            return Price;
        }

        public void setPrice(String price) {
            Price = price;
        }

        public String getQuantity() {
            return Quantity;
        }

        public void setQuantity(String quantity) {
            Quantity = quantity;
        }

        public String getTotalAmount() {
            return TotalAmount;
        }

        public void setTotalAmount(String totalAmount) {
            TotalAmount = totalAmount;
        }

        public String getTitle() {
            return Title;
        }

        public void setTitle(String title) {
            Title = title;
        }
    }

    public static class Title{
        String Name;

        public Title(String name) {
            Name = name;
        }

        public String getName() {
            return Name;
        }

        public void setName(String name) {
            Name = name;
        }
    }
}
