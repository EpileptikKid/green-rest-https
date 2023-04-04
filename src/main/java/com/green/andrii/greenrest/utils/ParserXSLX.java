package com.green.andrii.greenrest.utils;

import com.green.andrii.greenrest.models.Client;
import com.green.andrii.greenrest.models.Packing;
import com.green.andrii.greenrest.models.Product;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class ParserXSLX {
    private static String manager = null;
    private static Date date = new Date();
    private static String clientName = null;

    private static final DateFormat checkDate = new SimpleDateFormat("dd.MM.yyyy");
    public static List<Client> parse(XSSFWorkbook workbook) throws ParseException {
        List<Row> rows = new ArrayList<>();
        List<Client> clients = new ArrayList<>();
        Sheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIterator = sheet.iterator();
        parseRows(rows, rowIterator);
        int id = 0;

        for (Row row : rows) {
            String firstCellText = row.getCell(0).getStringCellValue();

            switch (checkingRowMatchesCategory(row)) {
                case "product" -> {
                    Product product = new Product(firstCellText,
                            row.getCell(1).getNumericCellValue(),
                            Packing.getPackingByText(row.getCell(2).getStringCellValue()), "n");
                    product.setClient(clients.get(id - 1));
                    clients.get(id - 1).getProducts().add(product);
                }
                case "client_name" -> clientName = firstCellText.substring(0, firstCellText.indexOf(","));
                case "comment" -> {
                    String comment = firstCellText.substring(firstCellText.lastIndexOf(",") + 1).trim();
                    Client clientToAdd = new Client(clientName, date, manager, comment, "n");
                    if (!clients.contains(clientToAdd)) {
                        clientToAdd.setProducts(new ArrayList<>());
                        clients.add(clientToAdd);
                        id = clients.size();
                    }
                }
                case "manager_name" -> manager = firstCellText;
                case "date" -> date = checkDate.parse(firstCellText.substring(0, 10));
                default -> throw new RuntimeException("Parse exception - " + checkingRowMatchesCategory(row));
            }
        }
        return clients;
    }

    private static void parseRows(List<Row> rows, Iterator<Row> rowIterator) {
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();
        rowIterator.next();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();
            if (!row.getCell(0).getStringCellValue().equals(""))
                rows.add(row);
            else
                break;
        }
    }

    private static String checkingRowMatchesCategory(Row row) {
        List<String> managerNameList = Arrays.asList("Гонтарь", "Ивчик", "Шунин");
        String regexDate = "^\\d{2}\\.\\d{2}\\.\\d{4}.*";
        Cell cell = row.getCell(0);
        String firstCellText = cell.getStringCellValue();

        if (managerNameList.contains(firstCellText))
            return "manager_name";
        if (firstCellText.matches(regexDate))
            return "date";
        if (manager != null && firstCellText.endsWith(", " + manager))
            return "client_name";
        if (firstCellText.startsWith("Счет на оплату"))
            return "comment";
        if (row.getCell(1).getCellType() == CellType.NUMERIC)
            return "product";
        return "undefined";
    }


}
