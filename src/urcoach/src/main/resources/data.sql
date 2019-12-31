
INSERT INTO urcoachIS.categorie (nome, descrizione) VALUES ('Definizione', 'CessCess');

INSERT INTO urcoachIS.personal_trainers (email, codice_fiscale, cognome, data_nascita, foto, nome, p_iva, password, verificato) VALUES ('ciao@ciao.it', 'jkhjjhkjhklljl', 'Fasano', '2019-12-05', null, 'Salvatore', 'dvdsvd', 'ciao', 1);

INSERT INTO urcoachIS.pacchetti (id_pacchetto, costo, data_creazione, descrizione, durata, foto, nome, categoria_nome, personal_trainer_email) VALUES
(1, 100, '2018-12-18', null, 20, null, 'Dimagrire in 3 giorni', 'Definizione', 'ciao@ciao.it'),
(2, 200, '2021-12-18', null, 20, null, 'Dimagrire in 3 giorni', 'Definizione', 'ciao@ciao.it'),
(3, 300, '2018-12-18', null, 20, null, 'Dimagrire in 3 giorni', 'Definizione', 'ciao@ciao.it'),
(4, 400, '2019-12-18', null, 20, null, 'Dimagrire in 3 giorni', 'Definizione', 'ciao@ciao.it'),
(5, 500, '2020-12-18', null, 20, null, 'Dimagrire in 3 giorni', 'Definizione', 'ciao@ciao.it');


