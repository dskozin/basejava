import java.util.Arrays;

/**
 * Array based storage for Resumes
 */
public class ArrayStorage {
    Resume[] storage = new Resume[10000];
    int currentSize = 0;

    void clear() {
        this.storage = new Resume[10000];
        this.currentSize = 0;
    }

    void save(Resume r) {
        this.storage[this.currentSize] = r;
        ++this.currentSize;
    }

    Resume get(String uuid) throws NullPointerException {
        for (int i = 0; i < this.currentSize; i++){
            if (uuid.equals(this.storage[i].uuid)){
                return storage[i];
            }
        }

        //throw new NullPointerException();
        return null;
    }

    void delete(String uuid) {
        for (int i = 0; i < this.currentSize; i++){
            //если находим заданный элемент
            if(uuid.equals(this.storage[i].uuid)){
                //занулляем его
                this.storage[i] = null;
                //если он не последний
                if (!(this.currentSize - 1 == i)){
                    //ставим на его место последний
                    //тут моделируется Map
                    //если ArrayList то нужно сдвигать каждый элемент
                    this.storage[i] = this.storage[this.currentSize - 1];
                }
                //и выходим из цикла
                --this.currentSize;
                break;
            }
        }
    }

    /**
     * @return array, contains only Resumes in storage (without null)
     */
    Resume[] getAll() {

        return Arrays.copyOfRange(this.storage,0,this.currentSize);
    }

    int size() {

        return this.currentSize;
    }
}

//    Реализуйте класс ArrayStorage: хранение резюме на основе массива (методы clear, get, save, delete, getAll, size).
//  Не используйте в решении коллекции, реализацию на их основе мы добавим позднее.
//        Протестируйте вашу реализацию, запустив MainTestArrayStorage.main(): в IDEA слева на полях зеленая стрелка.
//        Протестируйте вашу реализацию интерактивно с помощью MainArray.main().
//        Дополнительные материалы по IntelliJ IDEA
//        Idea Wiki (поставить кодировку UTF-8, поменять фонт по умолчанию на DejaVu)
//        Отладчик IntelliJ IDEA
//        Эффективная работа с кодом в IntelliJ IDEA
//        Optional
//
//        Модифицировать класс ArrayStorage: хранить все резюме в начале storage (без дырок null), чтобы не перебирать каждый раз все 10000 элементов.
//        Хранеие резюме в storage (от 0 до size-1 элементов null нет):
//
//        r1, r2, r3,..., rn, null, null,..., null
//<----  size ----->
//<----  storage.length (10000)---------->
//        Посмотреть на класс Arrays. Там есть полезные вещи, которые могут упростить код ArrayStorage.
//        Протестируйте реализацию, запустив MainArray.main(): в IDEA слева на полях зеленая стрелка.