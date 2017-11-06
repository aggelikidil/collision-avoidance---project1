ΕΙΣΑΓΩΓΗ
..........................................................................
Στα πλαίσια του μαθήματος, υλοποιήσαμε ένα σύστημα ανίχνευσης συγκρούσεων πεζών μέσω μιας εφαρμογής σε γλώσσα προγραμματισμού Java και της πλατφόρμας Android Studio. Η εφαρμογή λαμβάνει τιμές από τους αισθητήρες του κινητού τερματικού. Στην υλοποίησή μας χρησιμοποιήσαμε τους αισθητήρες Proximity Sensor και Accelerometer Sensor. Η εφαρμογή με βάση κάποιες τροποποιήσιμες παραμέτρους ειδοποιεί τον χρήστη σε περίπτωση επικείμενης σύγκρουσης. Οι παράμετροι αυτοί αντιστοιχούν στα κατώφλια που θέτει ο χρήστης στα Settings της εφαρμογής. Συγκεκριμένα, θέσαμε ως κριτήριο ενεργοποίησης του οπτικού και ηχητικού σήματος τα εξής γεγονότα:
η τιμή του Proximity αισθητήρα να είναι μικρότερη ή ίση από το κατώφλι που θέτει ο χρήστης και
η τιμή του Ζ άξονα του Acceleromerer αισθητήρα να είναι μεγαλήτερη από το κατώφλι που θέτει ο χρήστης.
Επιλέξαμε αυτή τη συνθήκη θεωρώντας οτι η συσκευή ανιχνεύει συγκρούσεις με την πρόσοψη.
Η ειδοποίηση γίνεται με τη μορφή ηχητικού και οπτικού μηνύματος. Το οπτικό μήνυμα εχει τη μορφή “Android Toast” και το ηχητικό μήνυμα γίνεται με την αναπαραγωγή ενός ήχου ειδοποίησης (warning.mp3). Τα μηνύματα ειδοποίησης (ηχητικό και οπτικό) παραμένουν ενεργά για διάρκεια ίση με την διάρκεια εμφάνισης της πιθανότητας σύγκρουσης. Η εφαρμογή παραμένει ανοικτή και λειτουργική και μετά από χρήση του “android home button”. 
Η πληροφορία των αισθητήρων παρουσιάζεται στην κεντρική οθόνη της εφαρμογής με την εξής δομή:
τύπος αισθητήρα, μονάδα μέτρησης και τιμή. 
Η εφαρμογή διαθέτει “menu bar” που περιλαμβάνει το λογότυπο (ic_launher) και το όνομα της εφαρμογής (collision avoidance) καθώς και “options menu”. 
Το “options menu” περιλαμβάνει την επιλογή των ρυθμίσεων (Application settings) και την επιλογή εξόδου από την εφαρμογή (Exit). 
Οι ρυθμίσεις της εφαρμογής επιτρέπουν την παραμετροποίηση των αισθητήρων που χρησιμοποιήθηκαν και πιο συγκεκριμένα: 
α)τη συχνότητα ελέγχου των τιμών (Sampling Rate σε Hz). Ο χρήστης στην ουσία επιλέγει κάποια περίοδο δειγματοληψίας την οποία στη συνέχεια μετατρέπουμε σε συχνότητα. Η μετατροπή αυτή γίνεται μέσω της κλάσης OnClickListenerStartMonitoring. 
β)το κατώφλι στο οποίο θα ενεργοποιείται η ειδοποίηση για τον Proximity Sensor (Proximity Sensor Alarm Value σε cm)
ΠΑΡΑΤΗΡΗΣΗ: Και στις δύο συσκευές που δοκιμάσαμε την εφαρμογή παρατηρήσαμε πως ο αισθητήρας λαμβάνει δύο τιμές,0.0 ή 1.0(API 4.2.2)/8.0(API 6.0.1). Παρ' όλα αυτά, αποφασίσαμε να αφήσουμε το seek bar για πιθανή μελλοντική χρήση με περισσότερες διακριτές τιμές.
και 
γ) τα κατώφλια για τον Accelerometer Sensor καθώς για αυτόν επιστρέφονται 3 τιμές, μία για κάθε άξονα (Accelerometer Sensor Alarm Value X,Y,Z σε m/s^2), εκ των οποίων λαμβάνουμε υπόψιν τον τελευταίο. Ο τρόπος ρύθμισης των παραμέτρων γίνεται μέσω sliders.
ΠΑΡΑΤΗΡΗΣΗ: Παρ' όλα αυτά, αποφασίσαμε να αφήσουμε τα δύο επιπλέον seek bar για πιθανή μελλοντική χρήση.
Τέλος, ο χρήστης έχει τη δυνατότητα να κλείσει την εφαρμογή με 2 τρόπους: 
είτε μέσα από το “options menu” με τη χρήση αντίστοιχης επιλογής είτε με τη χρήση του “android back button”. Και στις δύο περιπτώσεις εμφανίζεται Dialog box για την επιβεβαίωση της εξόδου.

............................................................................
Τεχνολογίες συστήματος ανάπτυξης:
1. Java Oracle SE 8
2. Android API 4.2.2 και 6.0.1
3. Android Studio (Εργαλείο ανάπτυξης της εφαρμογής Android)
............................................................................


ΑΝΑΛΥΣΗ ΚΩΔΙΚΑ
............................................................................


--> data (package)
Στο συγκεκριμένο πακέτο διαχειριζόμαστε:

	α) DataSettings (class)
	την κλάση  στην οποίο αρχικοποιούνται οι αρχικές τιμές της συχνότητας (frequency) του κατωφλιού του πρώτου αισθητήρα (fisrtsensorvalue) και 		των κατωφλιών για τον κάθε άξονα του δεύτερου αισθητήρα. Επίσης κάθε φορά που ο χρήστης πατάει το κουμπί SAVE οι τιμές τροποποιούνται και 		αποθηκεύονται στις αντίστοιχες μεταβλητές.

	β)SensorsData (class)
	την κλάση στην οποία αρχικοποιούνται οι αρχικές τιμές των αισθητήρων για την πρώτη φορά που θα ανοίξει ο χρήστης την εφαρμογή δηλαδή πριν 	ξεκινήσει η παρακολούθηση τους χωρίς να είναι ορατές στην αρχική οθόνη. Επίσης κατά τη διάρκεια της παρακολούθησης ανανεώνονται συνεχώς και 	είναι ορατές στον χρήστη.

--> datahandler (package)
Data Handler (class)
Η συγκεκριμένη κλάση διαχειρίζεται την ανάγνωση(LoadFromDisk) και την εγγραφή(SaveToDisk) από και πρός τον δίσκο αντίστοιχα των τιμών των seek bars για τις μεταβλητές του DataSettings.

-->savelisteners (package)
ListenersStorage (class)
Σε αυτή την κλάση αποθηκεύονται οι listeners που παρακολουθούν τις εναλλαγές των αισθητήρων για να μπορούν να είναι προσπελάσιμες μέσω του ονόματος της κλάσης (public static) απο τις κλάσεις OnClickListenerStartMonitoring και OnClickListenerStopMonitoring που θα τους χρειαστούν αμετάβλητους ώστε κάθε φορά να μην δημιουργούνται νέοι listeners αλλά οι υπάρχοντες να δείχνουν στο ίδιο αντικείμενο με το πάτημα των κουμπιών START/STOP.

-->onclicklisteners (package)
To πακέτo οnclicklisteners περιέχει τις κλάσεις προσδιορίζουν την ενέργεια που θα εκτελεστεί όταν πατηθεί το αντίστοιχο κουμπί (γεγονός).
	-->onclicklistener
	Η διεπαφή ανάμεσα στα γεγονότα, δηλαδή τα αντίστοιχα πατήματα κουμπίων και στις ενέργειες που θα εκτελεστούν στη συνέχεια ανάλογα με το κάθε 		κουμπί.
	
	α)OnClickListenerSaveData
	Η διεπαφή ανάμεσα στο πάτημα του κουμπιού SAVE και της ενέργειας που θα εκτελεστεί στη συνέχεια, δηλαδή την εγγραφή των δεδομένων στον δίσκο.
	
	β)OnClickListenerSettings
	Η διεπαφή ανάμεσα στο πάτημα του κουμπιού SETTINGS και της ενέργειας που θα εκτελεστεί στη συνέχεια, δηλαδή τη δημιουργία του νέου activity 		Settings.
	
	γ)OnClickListenerStartMonitoring
	Η διεπαφή ανάμεσα στο πάτημα του κουμπιού START και της ενέργειας που θα εκτελεστεί στη συνέχεια. Αρχικά διαβάζονται οι τιμές κατωφλιού και 		συχνότητας δειγματοληψίας για να ξεκινήσει η παρακολούθηση των επιλεγμένων αισθητήρων. Ταυτόχρονα γίνεται η σύνδεση των listeners των 		αισθητήρων μέσω της κλάσης ListenersStorage.
        ΠΑΡΑΤΗΡΗΣΗ: Στα API όπου δοκιμάσαμε την εφαρμογή υπάρχει η παράμετρος της συχνότητας. Μέσα στην κλάση OnClickListenerStartMonitoring γίνεται μετατροπή της συχνότητας που θέτει ο χρήστης στα settings σε περίοδο με μονάδα μέτρησης τα microsecond(μs). H συνάρτηση registerlistener παίρνει σαν τρίτη παράμετρο την περίοδο, ώστε να την μεταβιβάσει στην OnSensorChanged, η οποία καλείται από την πρώτη.

	δ)OnClickListenerStopMonitoring
	Η διεπαφή ανάμεσα στο πάτημα του κουμπιού STOP και της ενέργειας που θα εκτελεστεί στη συνέχεια. Σταματάει η παρακολούθηση των αισθητήρων και 		ταυτόχρονα γίνεται η αποσύνδεση των listeners των αισθητήρων μέσω της κλάσης ListenersStorage.

-->onprogresslisteners (package)
To πακέτo onprogresslisteners περιέχει τις κλάσεις που προσδιορίζουν την ενέργεια που θα εκτελεστεί όταν υπάρξει αλλαγή στον δρομέα του κάθε seek bar (γεγονός).

	α)OnSamplingRateProgressListener(class)
	Για κάθε εναλλαγή του seek bar της συχνότητας η τιμή εμφανίζεται στο αντίστοιχο text view μόνο αν ειναι μεγαλύτερη του 0. Διαφορετικά με τη 		χρήση ενός toast ενημερώνεται ο χρήστης για μη αποδεκτή τιμή ώστε να την αλλάξει. 
	
	β)OnFirstSensorProgressListener(class)
	Για κάθε εναλλαγή του seek bar του proxomity η τιμή εμφανίζεται στο αντίστοιχο text view μόνο αν ειναι μεγαλύτερη του 0. Διαφορετικά με τη 		χρήση ενός toast ενημερώνεται ο χρήστης για μη αποδεκτή τιμή ώστε να την αλλάξει.

	γ)OnSecondSensorProgressListenerX(class)
	Για κάθε εναλλαγή του seek bar του άξονα χ του accelerometer η τιμή εμφανίζεται στο αντίστοιχο text view μόνο αν ειναι μεγαλύτερη του 0. 		Διαφορετικά με τη χρήση ενός toast ενημερώνεται ο χρήστης για μη αποδεκτή τιμή ώστε να την αλλάξει.

	δ)OnSecondSensorProgressListenerY(class)
	Για κάθε εναλλαγή του seek bar του άξονα y του accelerometer η τιμή εμφανίζεται στο αντίστοιχο text view μόνο αν ειναι μεγαλύτερη του 0. 		Διαφορετικά με τη χρήση ενός toast ενημερώνεται ο χρήστης για μη αποδεκτή τιμή ώστε να την αλλάξει.

	ε)OnSecondSensorProgressListenerZ(class)
	Για κάθε εναλλαγή του seek bar του άξονα z του accelerometer η τιμή εμφανίζεται στο αντίστοιχο text view μόνο αν ειναι μεγαλύτερη του 0. 		Διαφορετικά με τη χρήση ενός toast ενημερώνεται ο χρήστης για μη αποδεκτή τιμή ώστε να την αλλάξει.

-->onsensoreventlisteners (package)
To πακέτo onsensoreventlisteners περιέχει τις κλάσεις που προσδιορίζουν την ενέργεια που θα εκτελεστεί όταν υπάρξει αλλαγή στην τιμή των αισθητήρων κατά την παρακολούθηση (γεγονός).
	
	α)OnProximitySensorEventListener(class)
	Για κάθε εναλλαγή των τιμών του αισθητήρα proximity οι τιμές αποθηκεύονται στην μεταβλητή proximity της κλάσης SensorData.

	β)OnAccelerometerSensorEventListenerX(class)
	Αρχικά δημιουργούμε 3 συναρτήσεις για την εγκατάσταση, την έναρξη και τον τερματισμό της μουσικής.
	Για κάθε εναλλαγή των τιμών του άξονα χ του αισθητήρα accelerometer οι τιμές αποθηκεύονται στην μεταβλητή x της κλάσης SensorData.
	Για κάθε εναλλαγή των τιμών του άξονα y του αισθητήρα accelerometer οι τιμές αποθηκεύονται στην μεταβλητή y της κλάσης SensorData.
	Για κάθε εναλλαγή των τιμών του άξονα z του αισθητήρα accelerometer οι τιμές αποθηκεύονται στην μεταβλητή z της κλάσης SensorData.
	Ορίζουμε τη συνθήκη ενεργοποίσης της ειδοποίησης όπως περιγράφεται στην εισαγωγή, η οποία ελέγχεται σε αυτή την κλάση καθώς η OnSensorChange 		καλείται συνεχώς λόγω της μεγάλης συχνότητας αλλαγής των τιμών των τριών αξόνων του Accelerometer sensor.

-->protoproject (package)
Το πακέτο που περιέχει τα δύο activities της υλοποίησής μας.

	α)MainActivity
	Πρόκειται για το βασικό activity της εφαρμογής. Ο χρήστης ανοίγοντας την εφαρμογή βλέπει τα 3 κουμπιά START, STOP, SETTINGS, το options menu, 		το logo μαζι με το όνομα της εφαρμογής και τα ονόματα των αισθητήρων που θα παρακολουθηθούν. Με το πάτημα  του κουμπιού START εμφανίζονται οι  		εναλλαγές στις τιμές των αισθητήρων. Μέσω του options menu μπορεί να μεταβεί στις ρυθμίσεις της εφαρμογής με την επιλογή Application Settings 		ή να βγεί απο την εφαρμογή με την επιλογή του exit.Ανάλογα με την επιλογή που κάνει, εμφανίζεται αντίστοιχο Android Toast (ExitSettings)       Για την έξοδο απο την εφαρμογή έχουμε δημιουργήσει την συνάρτηση exitapplication(); η οποία καλείται είτε όταν επιλέγεται έξοδος από το μενού είτε όταν πατηθεί το back button. Η συγκεκριμένη συνάρτηση εμφανίζει ένα παράθυρο για επιβεβαίωση εξόδου. Αν  επιβεβαιωθεί η έξοδος αποδεσμεύεται απο την μνήμη ο χώρος στον οποίο αποθηκεύτηκαν οι διευθύνσεις τον listeners των αισθητήρων.
	β)SettingsActivity
	Πρόκειται για το δευτερεύον activity της εφαρμογής. Ο χρήστης μπαίνοντας στις ρυθμίσεις ενημερώνεται με αντίστοιχο toast για το επιτυχές 		φόρτωμα των ρυθμίσεων από την μνήμη. Επιπλέον, βλέπει τα 5 seek bars που έχουν ήδη αναφερθεί, το κουμπί SAVE και το logo μαζι με το όνομα της 		εφαρμογής. Με το πάτημα  του κουμπιού SAVE αποθηκεύονται οι τρέχουσες τιμές των seek bar και εμφανίζεται αντίστοιχο μήνυμα toast για την 		επιτυχία ή μη της αποθήκευσης. Με το πάτημα του back button επιστρέφει και πάλι στο main activity της εφαρμογής.
...............................................................................................................................................

ΠΗΓΕΣ

http://www.oracle.com/technetwork/articles/java/index-137868.html
https://developer.android.com/reference/android/widget/SeekBar.OnSeekBarChangeListener.html
http://stackoverflow.com/questions/37244357/how-to-play-music-in-android-studio
http://stackoverflow.com/questions/3033135/android-seekbar-minimum-value
http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
http://stackoverflow.com/questions/17800615/android-studio-default-project-directory
http://stackoverflow.com/questions/26615889/how-to-change-the-launcher-logo-of-an-app-in-android-studio
http://stackoverflow.com/questions/33068542/how-to-get-context-from-on-click-method-in-dialogbox
http://stackoverflow.com/questions/2257963/how-to-show-a-dialog-to-confirm-that-the-user-wishes-to-exit-an-android-activity
http://stackoverflow.com/questions/24993825/handle-back-button-in-alertdialog-builder
http://stackoverflow.com/questions/10346011/how-to-handle-back-button-with-in-the-dialog
http://stackoverflow.com/questions/28564343/need-for-popbackstack-in-onbackpressed
http://stackoverflow.com/questions/26472417/logo-are-not-displayed-in-actionbar-using-appcompat
http://stackoverflow.com/questions/14570512/how-to-get-the-parent-view-of-a-button-within-a-list-adapter
http://stackoverflow.com/questions/5447092/get-context-inside-onclickdialoginterface-v-int-buttonid
https://developer.android.com/guide/topics/sensors/sensors_overview.html
http://stackoverflow.com/questions/15326290/get-android-seekbar-value-and-display-it-on-screen
http://stackoverflow.com/questions/9792888/android-seekbar-set-progress-value
http://stackoverflow.com/questions/14376807/how-to-read-write-string-from-a-file-in-android
http://stackoverflow.com/questions/35481924/write-a-string-to-a-file
http://stackoverflow.com/questions/18100485/how-to-create-and-or-append-to-a-file-on-android
http://stackoverflow.com/questions/13426142/bufferedwriter-not-writing-everything-to-its-output-file
http://stackoverflow.com/questions/4072706/the-connection-to-adb-is-down-and-a-severe-error-has-occured
