DROP DATABASE IF EXISTS urcoachIS;
CREATE DATABASE urcoachIS;
USE urcoachIS;

create table atleti
(
	email varchar(255) not null
		primary key,
	codice_fiscale varchar(255) null,
	cognome varchar(255) null,
	data_nascita date null,
	indirizzo_fatturazione varchar(255) null,
	nome varchar(255) null,
	password varchar(255) null
);

create table categorie
(
	nome varchar(255) not null
		primary key,
	descrizione varchar(255) null
);

create table fatture
(
	numero_fattura int auto_increment
		primary key,
	costo float not null,
	data date null,
	atleta_email varchar(255) null,
	constraint FKnbo001hae36dyrp3g9x2p85ko
		foreign key (atleta_email) references atleti (email)
);

create table personal_trainers
(
	email varchar(255) not null
		primary key,
	bio varchar(255) null,
	codice_fiscale varchar(255) null,
	cognome varchar(255) null,
	data_nascita date null,
	foto varchar(255) null,
	nome varchar(255) null,
	p_iva varchar(255) null,
	password varchar(255) null,
	verificato int not null
);

create table pacchetti
(
	id_pacchetto int auto_increment
		primary key,
	costo float not null,
	data_creazione date null,
	descrizione varchar(255) null,
	durata int not null,
	foto varchar(255) null,
	nome varchar(255) null,
	categoria_nome varchar(255) null,
	personal_trainer_email varchar(255) null,
	constraint FK8duqorg82tue06h2c8ofvj9ii
		foreign key (categoria_nome) references categorie (nome),
	constraint FKgs9r69sa3r8j9ukf6c2ler48n
		foreign key (personal_trainer_email) references personal_trainers (email)
);

create table acquisti
(
	costo float not null,
	pacchetto_id_pacchetto int not null,
	fattura_numero_fattura int not null,
	primary key (pacchetto_id_pacchetto, fattura_numero_fattura),
	constraint FKcbq38gvpx9iovuag064rurge0
		foreign key (pacchetto_id_pacchetto) references pacchetti (id_pacchetto),
	constraint FKljcnjqq540srf2k67ng3doeg9
		foreign key (fattura_numero_fattura) references fatture (numero_fattura)
);

