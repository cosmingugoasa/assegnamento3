# Assegnamento del corso "Ingegneria del Software" - Ing. Sistemi Informativi

L’obiettivo è definire le classi del sistema software che dovrà gestire i dati anagrafici degli impiegati
di un’azienda con diverse sedi lavorative.
Il sistema è composto da un server, che ha il compito di mantenere i dati anagrafici e fornire dei
servizi di ricerca, e da più client che hanno il compito di abilitare l’aggiunta e la modifica dei dati
anagrafici dalle diverse sedi lavorative.
Il sistema è definito da un insieme di sedi lavorative e di impiegati.
Ogni sede lavorativa è definita da un nome e un indirizzo. Due sedi lavorative non possono avere lo
stesso nome
Ogni impiegato è definito da: nome, cognome, codice fiscale, sede lavorativa e mansione lavorativa,
(operaio, funzionario, dirigente e amministratore), data di inizio attività ed eventuale data di fine
attività. Il codice fiscale dovrà avere la lunghezza standard (16 caratteri), ma, per semplicità, sarà
composto da caratteri e cifre numeriche generate eventualmente in modo casuale. Due persone non
possono avere lo stesso codice fiscale
I funzionari possono accedere al server tramite login e password e hanno il compito di inserire i nuovi
impiegati e aggiornare la loro anagrafica. Ovviamente il sistema dovrà bloccare l’assegnamento di un
codice fiscale a una persona se esiste già un’altra persona con quel codice fiscale e, in questi casi,
dovrà notificare l’errore al funzionario.
I dirigenti possono accedere al server tramite login e password e possono fare ricerche (numero e lista
persone per mansione e accesso ai loro dati anagrafici) su tutto il personale eccetto che sugli
amministratori.
Gli amministratori possono accedere al server tramite login e password e possono fare ricerche
(numero e lista persone per mansione e accesso ai loro dati anagrafici) su tutto il personale.
Definite le classi, bisognerà definire il caso di uso relativo all’inserimento di un nuovo dipendente
(con eventuali flussi alternativi e di eccezione) e implementare una semplice simulazione che:
1) crea dei piccoli insiemi iniziali di sedi lavorative e i dati anagrafici di alcuni dipendenti con il
vincolo che ogni sede lavorativa deve avere almeno un funzionario;
2) in modo casuale ogni funzionari crea o aggiorna dei dipendenti con il vincolo che la sequenza
delle operazioni di ognuno di loro è intermezzata da un’attesa di durata casuale e che tra i
possibili valori del codice fiscale da assegnare a una persona ci siano i codici fiscali delle
persone già memorizzate;
3) in modo casuale ogni dirigente e amministratore fa una ricerca sul numero o la lista di persone
di una sede lavorativa con il vincolo che la sequenza delle operazioni di ognuno di loro è
intermezzata da un’attesa di durata casuale;
4) La simulazione e quindi l’esecuzione di ogni client terminerà dopo una durata casuale che
dovrà avere comunque un valore minimo tale da poter garantire ad ogni “attore” l’esecuzione
di un numero di azioni sufficienti per “testare” le funzionalità del sistema e la “buona”
gestione della concorrenza.
5) Infine il server terminerà la sua attività quando tutti i client avranno terminato e quindi chiuso
il loro collegamento con il server stesso.
Ovviamente, l’avanzamento corretto della simulazione dovrà essere descritto tramite semplici
operazione di scrittura su console.
Il sistema dovrà essere consegnato come un “progetto maven” condiviso con il docente tramite un
sito di condivisione (e.g., Dropbox e Google drive). Il docente alla sua ricezione invierà una risposta
di ricezione. Il sistema dovrà essere consegnato non più tardi di una settimana dall’ultimo laboratorio
riguardante l’assegnamento (i.e., in base alla difficoltà, un assegnamento potrà coinvolgere più di una
lezione in laboratorio).

## Credits
Cosmin Gugoasa - 282322 - L.I.S.I.


