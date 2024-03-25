package uz.studentsproject.utils;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Endpoint {


    public static final String STUDENT = "/api/student";
    public static final String UNIVERSITY = "/api/university";

    public static final String FILE_STORAGE = "/api/filestorage";

    public static final String UPLOAD = "/upload";
    public static final String FIELD_OF_STUDY = "/api/fieldofstudy";
    public static final String CREATE_OR_UPDATE = "";
    public static final String GET_BY_ID = "/{id}";

    public static final String GET_BY_HASH_ID = "/getbyhashid";

    public static final String GET_BY_FILE_ID = "/getbyid";
    public static final String UPDATE = "/{id}";
    public static final String DELETE = "/{id}";

    public static final String DELETE_BY_HASH_ID = "/deletebyhashid";
    public static final String GETALL = "/getall";

    public static final String PREVIEW = "/preview";

    public static final String EXPORT = "/api/export";

    public static final String EXCEL = "/excel";

    public static final String PDF = "/pdf";

    public static final String PDF_RESUME = "/pdfresume/{id}";


}
