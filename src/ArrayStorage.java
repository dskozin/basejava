import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int storageSize = 0;

    void clear() {
        this.storage = new Resume[10000];
        this.storageSize = 0;
    }

    void save(Resume r) {
        this.storage[this.storageSize] = r;
        ++this.storageSize;
    }

    Resume get(String uuid) throws NullPointerException {
        for (int i = 0; i < this.storageSize; i++){
            if (uuid.equals(this.storage[i].uuid)){
                return storage[i];
            }
        }

        //throw new NullPointerException();
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < this.storageSize; i++){
            //если находим заданный элемент
            if(uuid.equals(this.storage[i].uuid)){
                //занулляем его
                this.storage[i] = null;
                //если он не последний
                if (!(this.storageSize - 1 == i)){
                    //ставим на его место последний
                    //тут моделируется Map
                    //если ArrayList то нужно сдвигать каждый элемент
                    this.storage[i] = this.storage[this.storageSize - 1];
                    //последний тоже зануляем, просто освободить ячейку, удалить объект
                    this.storage[this.storageSize - 1] = null;
                }
                //и выходим из цикла
                --this.storageSize;
                return;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOfRange(this.storage,0,this.storageSize);
    }

    int size() {

        return this.storageSize;
    }
}