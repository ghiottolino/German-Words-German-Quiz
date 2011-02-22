Title (en): German words gender quiz

Description (en): The quiz consists of guessing the gender of a German word. At every stage you will see a random German noun, and you can guess its gender by clicking on the 3 buttons ('der' for masculine, 'die' for feminine and 'das' for neutral words). The database contains about 450 basic words, and it will be extended in the next releases. While playing no internet access is required.

Promo Text (en): Test your knowledge of German words gender with this educational game.

Email: ghiottolino+Germanwordsgenderquiz@gmail.com

VERSION

0.2 Fixed and improved UI

0.3 Improve algorithm for selecting the word to guess (you will learn better because if you give a wrong answer you will have to answer to the same quiz again few games afterwards).

0.4 Added record functionality

0.5 Change order of buttons

INSTRUCTIONS FOR BUILDING

./adb uninstall com.nicolatesser.germangenderquiz

jarsigner -verbose -keystore keys/android.keystore GermanGenderQuiz.apk android

/home/tex/tools/android-sdk-linux_x86/tools/zipalign -v 4 /home/tex/GermanGenderQuiz.apk /home/tex/GermanGenderQuiz2.apk
 
rm GermanGenderQuiz.apk

mv GermanGenderQuiz2.apk GermanGenderQuiz.apk
