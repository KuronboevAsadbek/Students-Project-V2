package uz.studentsproject.exception;

import java.io.Serial;

public class FileStorageException extends RuntimeException{
    @Serial
    private static final long serialVersionUID = -8600724553044630388L;

    public FileStorageException(String message) {
        super(message);
    }
}
