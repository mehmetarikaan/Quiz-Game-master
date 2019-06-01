package sarveshchavan777.triviaquiz;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import arsey.ehliyetsinavisorulari.R;
import java.util.ArrayList;
import java.util.List;


class TriviaQuizHelper extends SQLiteOpenHelper {

    private Context context;
    private static final String DB_NAME = "TQuiz.db";

    //If you want to add more questions or wanna update table values
    //or any kind of modification in db just increment version no.
    private static final int DB_VERSION = 3;
    //Table name
    private static final String TABLE_NAME = "TQ";
    //Id of question
    private static final String UID = "_UID";
    //Question
    private static final String QUESTION = "QUESTION";
    //Option A
    private static final String OPTA = "OPTA";
    //Option B
    private static final String OPTB = "OPTB";
    //Option C
    private static final String OPTC = "OPTC";
    //Option D
    private static final String OPTD = "OPTD";
    //Answer
    private static final String ANSWER = "ANSWER";
    //So basically we are now creating table with first column-id , sec column-question , third column -option A, fourth column -option B , Fifth column -option C , sixth column -option D , seventh column - answer(i.e ans of  question)
    private static final String CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + " ( " + UID + " INTEGER PRIMARY KEY AUTOINCREMENT , " + QUESTION + " VARCHAR(255), " + OPTA + " VARCHAR(255), " + OPTB + " VARCHAR(255), " + OPTC + " VARCHAR(255), " + OPTD + " VARCHAR(255), " + ANSWER + " VARCHAR(255));";
    //Drop table query
    private static final String DROP_TABLE = "DROP TABLE IF EXISTS " + TABLE_NAME;

    TriviaQuizHelper(Context context) {
        super(context, DB_NAME, null, DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        //OnCreate is called only once
        sqLiteDatabase.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        //OnUpgrade is called when ever we upgrade or increment our database version no
        sqLiteDatabase.execSQL(DROP_TABLE);
        onCreate(sqLiteDatabase);
    }

    void allQuestion() {
        ArrayList<TriviaQuestion> arraylist = new ArrayList<>();

        arraylist.add(new TriviaQuestion("Ülkemizde 112 acil telefon hattı, öncelikle hangi hizmet için kullanılmaktadır?", "İtfaiye", "Ambulans", "Polis imdat", "Jandarma imdat", "Ambulans"));

        arraylist.add(new TriviaQuestion("Aşağıdakilerden hangisi, kazazedenin ikinci değerlendirilme aşamalarından olan “Baştan Aşağı Kontrol” basamağında yer alır?", "Adı ve soyadının öğrenilmesi", "Kullandığı ilaçların belirlenmesi", "Kişisel özgeçmişinin öğrenilmesi", "Solunum sayısının değerlendirilmesi", "Euclid"));

        arraylist.add(new TriviaQuestion("Aşağıdakilerden hangisi, kaza sonrası güvenli bir ortamın oluşturulması için yapılması gereken uygulamalardandır?", "Yardımı güçleştirecek meraklı kişilerin olay yerinden uzaklaştırılması", "Olay yerinin diğer araç sürücüleri tarafından görünmesinin engellenmesi", "Araç LPG´li ise bagajında bulunan tüpün vanasının kapatılmaması", "Kazaya uğrayan aracın kontağının açık bırakılması", "Sardar Vallabhbhai Patel"));

        arraylist.add(new TriviaQuestion("Yetişkinlere ve bebeklere yapılan temel yaşam desteği uygulamasında, göğüs kemiği kaç cm aşağı inecek şekilde kalp basısı uygulanır? Yetişkin ——– Bebek", "2 ------------ 1", "3 ------------ 2", "5 ------------ 4", "7 ------------ 6", "Valentina Tereshkova"));

        arraylist.add(new TriviaQuestion("İlk yardımcı olarak bulunduğunuz bir kaza yerinde, kazazedelerden birinin öksürme ile ağzından açık kırmızı renkte, köpüklü kan geldiğini gözlemlediniz. Bu durumda, aşağıdaki organlardan hangisinin yaralandığını düşünürsünüz?", "Böbrek", "Akciğer", "Bağırsak", "Karaciğer", "Milkha singh"));

        arraylist.add(new TriviaQuestion("Kalp-damar sisteminin yaşamsal organlara uygun oranda kanlanma yapamaması nedeniyle ortaya çıkan, tansiyon düşüklüğü ile seyreden ve ani gelişen dolaşım yetmezliğine – – – – denir. Bu ifadede boş bırakılan yere aşağıdakilerden hangisi yazılmalıdır?", "Şok", "Havale", "Epilepsi", "Bayılma", "Shakunthala Devi"));

        arraylist.add(new TriviaQuestion("Kalp-damar sisteminin yaşamsal organlara uygun oranda kanlanma yapamaması nedeniyle ortaya çıkan, tansiyon düşüklüğü ile seyreden ve ani gelişen dolaşım yetmezliğine – – – – denir. Bu ifadede boş bırakılan yere aşağıdakilerden hangisi yazılmalıdır?", "Şok", "Havale", "Epilepsi", "Bayılma", "Shakunthala Devi"));

        this.addAllQuestions(arraylist);

    }


    private void addAllQuestions(ArrayList<TriviaQuestion> allQuestions) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        try {
            ContentValues values = new ContentValues();
            for (TriviaQuestion question : allQuestions) {
                values.put(QUESTION, question.getQuestion());
                values.put(OPTA, question.getOptA());
                values.put(OPTB, question.getOptB());
                values.put(OPTC, question.getOptC());
                values.put(OPTD, question.getOptD());
                values.put(ANSWER, question.getAnswer());
                db.insert(TABLE_NAME, null, values);
            }
            db.setTransactionSuccessful();
        } finally {
            db.endTransaction();
            db.close();
        }
    }


    List<TriviaQuestion> getAllOfTheQuestions() {

        List<TriviaQuestion> questionsList = new ArrayList<>();
        SQLiteDatabase db = this.getWritableDatabase();
        db.beginTransaction();
        String coloumn[] = {UID, QUESTION, OPTA, OPTB, OPTC, OPTD, ANSWER};
        Cursor cursor = db.query(TABLE_NAME, coloumn, null, null, null, null, null);


        while (cursor.moveToNext()) {
            TriviaQuestion question = new TriviaQuestion();
            question.setId(cursor.getInt(0));
            question.setQuestion(cursor.getString(1));
            question.setOptA(cursor.getString(2));
            question.setOptB(cursor.getString(3));
            question.setOptC(cursor.getString(4));
            question.setOptD(cursor.getString(5));
            question.setAnswer(cursor.getString(6));
            questionsList.add(question);
        }

        db.setTransactionSuccessful();
        db.endTransaction();
        cursor.close();
        db.close();
        return questionsList;
    }
}
