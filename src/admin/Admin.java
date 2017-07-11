package admin;

import menuBar.MenuBarControl;
import profileView.Info;
import profileView.ProfileView;

import database.DBConnection;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;


import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ResourceBundle;


public class Admin implements Initializable {

    /**
     * Atribut ini digunakan untuk mengAssign tableVIew pada interface Admin.fxml
     */
    @FXML
    TableView<AdminTable> adminTableView;
    @FXML
    TableColumn<AdminTable,Integer> adminTableStudentNo;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentName;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentID;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentDepartment;
    @FXML
    TableColumn<AdminTable,Double> adminTableStudentCGPA;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentPhone;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentDOB;
    @FXML
    TableColumn<AdminTable,String> adminTableGuardianName;
    @FXML
    TableColumn<AdminTable,String> adminTableGuardianEmail;
    @FXML
    TableColumn<AdminTable,String> adminTableGuardianPhone;
    @FXML
    TableColumn<AdminTable,String> adminTableStudentAddress;


    /**
     * Atribut ini digunakan untuk mengAssign Button pada interface Admin.fxml
     */
    @FXML
    private Button adminClearButtonClick;
    @FXML
    private Button adminSaveButtonClick;


    /**
     * Atribut ini digunakan untuk mengAssign TextField pada interface Admin.fxml
     */
    @FXML
    private TextField adminTFStudentFname;
    @FXML
    private TextField adminTFStudentLname;
    @FXML
    private TextField adminTFStudentID;
    @FXML
    private ChoiceBox adminCBStudentDepartment;
    @FXML
    private TextField adminTFStudentEmail;
    @FXML
    private TextField adminTFStudentPhone;
    @FXML
    private TextField adminTFStudentAddress;
    @FXML
    private DatePicker adminDPStudentDOB;
    @FXML
    private TextField adminTFGuardianFname;
    @FXML
    private TextField adminTFGuardianLname;
    @FXML
    private TextField adminTFGuardianEmail;
    @FXML
    private TextField adminTFGuardianPhone;
    @FXML
    private TextField adminTFSearch;


    /**
     * Atribut ini digunakan untuk mengkoneksikan ke database
     */
    private DBConnection database = new DBConnection();
    private Connection connection;
    private Statement statement;
    private ResultSet resultSet;


    private boolean isSetAdminAddNewButtonClick;
    private boolean isSetAdminEditButtonClick;
    private MenuBarControl menuBarControl = new MenuBarControl();

    private String temp;


    /**
     * method Observablelist di gunakan untuk menerima data dari database ke aplikasi kita
     * @param query
     * @return
     */
    private ObservableList getDataFromSqlAndAddToObservableList(String query){
        ObservableList<AdminTable> adminTableData = FXCollections.observableArrayList();
        try {

            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(query);//"SELECT * FROM students;"
            while(resultSet.next()){
                adminTableData.add(new AdminTable(
                        resultSet.getInt("dbStudentSerialNo"),
                        resultSet.getString("dbStudentFname")+" "+resultSet.getString("dbStudentLname"),
                        resultSet.getString("dbStudentID"),
                        resultSet.getString("dbStudentDepartment"),
                        resultSet.getDouble("dbStudentCgpa"),
                        resultSet.getString("dbStudentPhone"),
                        resultSet.getString("dbStudentDOB"),
                        resultSet.getString("dbGuardianFname")+" "+resultSet.getString("dbGuardianLname"),
                        resultSet.getString("dbGuardianEmail"),
                        resultSet.getString("dbGuardianPhone"),
                        resultSet.getString("dbStudentAddress")

                        ));
            }
            connection.close();
            statement.close();
            resultSet.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return adminTableData;

    }

    /**
     * methode initialize adalah konstruktor dari class controller "admin"
     * karena kelas admin mengimplement kelas Initalizable
     * semua yang ada di dalam method ini akan run otomatis ketika aplikasi berjalan
     * @param location
     * @param resources
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        /**
         * data yang diambil dari database oleh getDataFromSqlAndAddToObservableList();
         * akan di terima oleh kelas AdminTable yang disebut kelas POJO (plain old java object)
         * kemudian akan ditambilkan di tableColumn menggunakan method setCellValueFactory
         */


        adminTableStudentNo.setCellValueFactory(new PropertyValueFactory<AdminTable,Integer>("adminTableDataNo"));
        adminTableStudentName.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentName"));
        adminTableStudentID.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentID"));
        adminTableStudentDepartment.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentDepartment"));
        adminTableStudentCGPA.setCellValueFactory(new PropertyValueFactory<AdminTable, Double>("adminTableDataStudentCGPA"));
        adminTableStudentPhone.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentPhone"));
        adminTableStudentDOB.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentDOB"));
        adminTableGuardianName.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataGuardianName"));
        adminTableGuardianEmail.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataGuardianEmail"));
        adminTableGuardianPhone.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataGuardianPhone"));
        adminTableStudentAddress.setCellValueFactory(new PropertyValueFactory<AdminTable, String>("adminTableDataStudentAddress"));

        //setelah itu kita setItem2 dari observableList kedalam TableView
        adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM student;"));

    }

    /**
     * method untuk menjalankan Button Add Student
     * dan mengijinkan (enable) untuk pengisian form
     * @param event
     */
    @FXML
    private void setAdminAddNewButtonClick(Event event){
        adminSetAllEnable();
        isSetAdminAddNewButtonClick = true;
    }

    /**
     * methode untuk  membuka akses form setelah
     * button add student di click
     */
    private void adminSetAllEnable(){
        adminTFStudentFname.setDisable(false);
        adminTFStudentLname.setDisable(false);
        adminTFStudentID.setDisable(false);
        adminCBStudentDepartment.setDisable(false);
        adminTFStudentEmail.setDisable(false);
        adminTFStudentPhone.setDisable(false);
        adminTFStudentAddress.setDisable(false);
        adminDPStudentDOB.setDisable(false);
        adminTFGuardianFname.setDisable(false);
        adminTFGuardianLname.setDisable(false);
        adminTFGuardianEmail.setDisable(false);
        adminTFGuardianPhone.setDisable(false);


        adminSaveButtonClick.setDisable(false);
        adminClearButtonClick.setDisable(false);

    }


    private void adminSetAllDisable(){
        adminTFStudentFname.setDisable(true);
        adminTFStudentLname.setDisable(true);
        adminTFStudentID.setDisable(true);
        adminCBStudentDepartment.setDisable(true);
        adminTFStudentEmail.setDisable(true);
        adminTFStudentPhone.setDisable(true);
        adminTFStudentAddress.setDisable(true);
        adminDPStudentDOB.setDisable(true);
        adminTFGuardianFname.setDisable(true);
        adminTFGuardianLname.setDisable(true);
        adminTFGuardianEmail.setDisable(true);
        adminTFGuardianPhone.setDisable(true);


        adminSaveButtonClick.setDisable(true);
        adminClearButtonClick.setDisable(true);

    }

    /**
     * method untuk menjalankan Button save
     * serta mengInput data dari form ke database
     * @param event
     */
    @FXML
    private void setAdminSaveButtonClick(Event event){
        int id = 0;
        id++;
        try{
            connection = database.getConnection();
            statement = connection.createStatement();

            /**
             * sql query untuk insert file dari form ke database
             */
            if(isSetAdminAddNewButtonClick){
                int rowsAffected = statement.executeUpdate("insert into`student` "+
                        "(`dbStudentFname`,`dbStudentLname`,`dbStudentID`,`dbStudentDOB`,"+
                        "`dbStudentDepartment`,`dbStudentEmail`,`dbStudentPhone`,`dbStudentAddress`," +
                        "`dbGuardianFname`,`dbGuardianLname`,"+
                        "`dbGuardianEmail`,`dbGuardianPhone`"+") "+
                        "values ('"+adminTFStudentFname.getText()
                        +"','"+adminTFStudentLname.getText()
                        +"','"+adminTFStudentID.getText()+id
                        +"','"+adminDPStudentDOB.getValue()
                        +"','"+adminCBStudentDepartment.getValue().toString().trim()
                        +"','"+adminTFGuardianEmail.getText()
                        +"','"+adminTFStudentPhone.getText()
                        +"','"+adminTFStudentAddress.getText()
                        +"','"+adminTFGuardianFname.getText()
                        +"','"+adminTFGuardianLname.getText()
                        +"','"+adminTFGuardianEmail.getText()
                        +"','"+adminTFGuardianPhone.getText()

                        +"'); ");
                statement.executeUpdate("insert into `studentgpa` (`dbstudentgpaID`) VALUES ('"+adminTFStudentID.getText()+"')");
            }
            //ketika edit button di klik maka akan mengupdate data dari form ke database
            else if (isSetAdminEditButtonClick){

                int rowsAffected = statement.executeUpdate("update student set "+
                        "dbStudentFname = '"+adminTFStudentFname.getText()+"',"+
                        "dbStudentLname = '"+adminTFStudentLname.getText()+"',"+
                        "dbStudentID = '"+adminTFStudentID.getText()+"',"+
                        "dbStudentDOB = '"+adminDPStudentDOB.getValue()+"',"+
                        "dbStudentDepartment = '"+adminCBStudentDepartment.getValue().toString().trim()+"',"+
                        "dbStudentEmail = '"+adminTFStudentEmail.getText()+"',"+
                        "dbStudentPhone = '"+adminTFStudentPhone.getText()+"',"+
                        "dbStudentAddress = '"+adminTFStudentAddress.getText()+"',"+
                        "dbGuardianFname = '"+adminTFGuardianFname.getText()+"',"+
                        "dbGuardianLname = '"+adminTFGuardianLname.getText()+"',"+
                        "dbGuardianEmail = '"+adminTFGuardianEmail.getText()+"',"+
                        "dbGuardianPhone = '"+adminTFGuardianPhone.getText()+

                        "' where dbStudentID = '"+
                        temp+"';");
                //jika String temp sama dengan adminTFStudentID maaka jalankna perintah update
                if (temp.equals(adminTFStudentID.getText())){
                    statement.executeUpdate("update studentgpa set dbstudentgpaID ='"+adminTFStudentID.getText()+"' where dbStudentID = '"+ temp+"';");
                }
            }

            /**
             * close koneksi dari database supaya tidak memberatkan aplikasi
             */
            connection.close();
            statement.close();
            resultSet.close();
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        /**
         * setelah selesai di save dan di Edit
         * semua form di clear kan dan dibuat unEnable
         */
        adminSetAllClear();
        adminSetAllDisable();

        adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM student;"));
        isSetAdminEditButtonClick=false;
        isSetAdminAddNewButtonClick = false;
    }

    /**
     * methode untuk setClear semua form registrasi student di panel admin
     */
    private void adminSetAllClear(){
        adminTFStudentFname.clear();
        adminTFStudentLname.clear();
        adminTFStudentID.clear();
        adminTFStudentEmail.clear();
        adminTFStudentPhone.clear();
        adminTFStudentAddress.clear();
        adminTFGuardianFname.clear();
        adminTFGuardianLname.clear();
        adminTFGuardianEmail.clear();
        adminTFGuardianPhone.clear();
    }

    @FXML
    private void setAdminClearButtonClick(Event event){
        adminSetAllClear();
        adminSetAllDisable();
        isSetAdminEditButtonClick=false;
        isSetAdminAddNewButtonClick = false;
    }


    /**
     * methode untuk menjalankan Button refresh
     * @param event
     */
    @FXML
    private void setAdminRefreshButtonClick(Event event){
        adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM student;"));//sql Query
        adminTFSearch.clear();
    }

    /**
     * methode untuk menjalankan Button cari
     * base on Id dari student
     * jika penulisan ID tidak lengkap maka tidak akan terdetek
     * @param event
     */
    @FXML
    private void setAdminSearchButtonClick(Event event){
        String sqlQuery = "select * FROM student where dbStudentID = '"+adminTFSearch.getText()+"';";
        adminTableView.setItems(getDataFromSqlAndAddToObservableList(sqlQuery));
    }


    /**
     * method untuk menjalankan button edit yang bertujuan
     * mengedit data dari table yang kita pilih (selected row)
     * @param event
     */
    @FXML
    private void setAdminEditButtonClick(Event event){
        AdminTable getSelectedRow = adminTableView.getSelectionModel().getSelectedItem();


        String sqlQuery = "select * FROM student where dbStudentID = '"+getSelectedRow.getAdminTableDataStudentID()+"';";

        try {
            connection = database.getConnection();
            statement = connection.createStatement();
            resultSet = statement.executeQuery(sqlQuery);
            adminSetAllEnable();
            while(resultSet.next()) {
                adminTFStudentFname.setText(resultSet.getString("dbStudentFname"));
                adminTFStudentLname.setText(resultSet.getString("dbStudentLname"));
                adminTFStudentID.setText(resultSet.getString("dbStudentID"));
                adminCBStudentDepartment.setValue(resultSet.getString("dbStudentDepartment"));
                adminTFStudentEmail.setText(resultSet.getString("dbStudentEmail"));
                adminTFStudentPhone.setText(resultSet.getString("dbStudentPhone"));
                adminTFStudentAddress.setText(resultSet.getString("dbStudentAddress"));
                try {
                    if (!(resultSet.getString("dbStudentDOB").isEmpty())) {
                        adminDPStudentDOB.setValue(LocalDate.parse(resultSet.getString("dbStudentDOB")));
                    }
                }
                catch (NullPointerException e){
                    adminDPStudentDOB.setValue(null);
                }

                adminTFGuardianFname.setText(resultSet.getString("dbGuardianFname"));
                adminTFGuardianLname.setText(resultSet.getString("dbGuardianLname"));
                adminTFGuardianEmail.setText(resultSet.getString("dbGuardianEmail"));
                adminTFGuardianPhone.setText(resultSet.getString("dbGuardianPhone"));

            }

            temp=adminTFStudentID.getText();
            isSetAdminEditButtonClick = true;
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

    }


    /**
     * method untuk menghapus data dari table dan database
     * @param event
     */
    @FXML
    private void setAdminDeleteButtonClick(Event event){

        AdminTable getSelectedRow = adminTableView.getSelectionModel().getSelectedItem();
        String sqlQuery = "delete from student where dbStudentID = '"+getSelectedRow.getAdminTableDataStudentID()+"';";
        try {
            connection = database.getConnection();
            statement = connection.createStatement();

            statement.executeUpdate(sqlQuery);
            statement.executeUpdate("delete from studentgpa where dbstudentgpaID ='"+getSelectedRow.getAdminTableDataStudentID()+"';");
            adminTableView.setItems(getDataFromSqlAndAddToObservableList("SELECT * FROM student;"));

        }
        catch (SQLException e) {
            e.printStackTrace();
        }


        //adminTableView.setItems(getDataFromSqlAndAddToObservableList(sqlQuery));

    }

    /**
     * untuk menampilkan data yang kita pilih di tableview(selected row) ke dalam profileView.fxml
     * @param event
     * @throws IOException
     */
    @FXML
    private void setAdminViewButtonClick(Event event) throws IOException {

        Info info = new Info(adminTableView.getSelectionModel().getSelectedItem().getAdminTableDataStudentID());
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/profileView/profileView.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));

        ProfileView profileView = loader.getController();
        profileView.setCurrentInfo(info);
        stage.show();

    }

    /**
     * method untuk menjalankan sub menu Exit dari menu File
     * @param event
     */
    @FXML
    private void setAdminCloseButtonClick(Event event){
        menuBarControl.close();
    }

    /**
     * method untuk menjalankan sub menu About us dari menu about
     * @param event
     * @throws IOException
     */
    @FXML
    private void setAdminAboutButtonClick(Event event) throws IOException {
        menuBarControl.about();
    }


    /**
     * method untuk menjalankan sub menu course panel
     * @param event
     * @throws IOException
     */
    @FXML
    private void setAdminCoursePanelClick(Event event) throws IOException {

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/Course.fxml"));
        loader.load();
        Parent p = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(p));
        stage.setTitle("Course Panel");
        stage.show();
    }

    /**
     * method untuk menjalankna sub menu teacher panel
     * @param event
     * @throws IOException
     */
    @FXML
    private void setAdminTeacherPanelClick(Event event)throws IOException{

        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("/admin/AdminTeacher.fxml"));
        loader.load();
        Parent pa = loader.getRoot();
        Stage stage = new Stage();
        stage.setScene(new Scene(pa));
        stage.setTitle("Teacher Panel");
        stage.show();

    }



}
