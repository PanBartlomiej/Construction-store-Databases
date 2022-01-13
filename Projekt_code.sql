CREATE SCHEMA projekt1;

CREATE TABLE projekt1.projekt1_log (

CREATE TABLE projekt1.projekt1_log (
    nazwa_tablicy varchar(20) not null,
    operacja varchar,
    nowy_rekord text,
    tgname text,
    tgwhen text,
    tglevel text,
    time_at timestamp not null default now(),
    userid name not null default session_user
);



CREATE TABLE projekt1.Typy_produktu (
                typ VARCHAR NOT NULL,
                CONSTRAINT typy_produktu_pk PRIMARY KEY (typ)
);


CREATE TABLE projekt1.klienci (
                id_klient INTEGER NOT NULL,
                imie VARCHAR(10000) NOT NULL,
                nazwisko VARCHAR(10000) NOT NULL,
                adres VARCHAR(10000) NOT NULL,
                CONSTRAINT klienci_pk PRIMARY KEY (id_klient)
);


CREATE TABLE projekt1.dostawcy (
                nip INTEGER NOT NULL,
                nazwa VARCHAR(10000) NOT NULL,
                adres VARCHAR(10000) NOT NULL,
                CONSTRAINT dostawcy_pk PRIMARY KEY (nip)
);


CREATE TABLE projekt1.admin (
                id_admin INTEGER NOT NULL,
                login VARCHAR NOT NULL,
                haslo VARCHAR NOT NULL,
                CONSTRAINT admin_pk PRIMARY KEY (id_admin)
);


CREATE TABLE projekt1.magazyny (
                id_magazynu INTEGER NOT NULL,
                nazwa VARCHAR(10000) NOT NULL,
                adres VARCHAR(10000) NOT NULL,
                id_admin INTEGER NOT NULL,
                CONSTRAINT magazyny_pk PRIMARY KEY (id_magazynu)
);


CREATE TABLE projekt1.dostawy (
                id_dostawy INTEGER NOT NULL,
                id_magazynu INTEGER NOT NULL,
                nip INTEGER NOT NULL,
                data DATE NOT NULL,
                CONSTRAINT dostawy_pk PRIMARY KEY (id_dostawy, id_magazynu)
);


CREATE TABLE projekt1.Produkty (
                id_produktu INTEGER NOT NULL,
                id_magazynu INTEGER NOT NULL,
                typ VARCHAR NOT NULL,
                ilosc INTEGER NOT NULL,
                nazwa VARCHAR NOT NULL,
                cena DOUBLE PRECISION NOT NULL,
                CONSTRAINT produkty_pk PRIMARY KEY (id_produktu, id_magazynu)
);


CREATE TABLE projekt1.faktury (
                id_faktury INTEGER NOT NULL,
                id_klient INTEGER NOT NULL,
                id_produktu INTEGER NOT NULL,
                id_magazynu INTEGER NOT NULL,
                ilosc INTEGER NOT NULL,
                CONSTRAINT faktury_pk PRIMARY KEY (id_faktury, id_klient, id_produktu, id_magazynu)
);


ALTER TABLE projekt1.Produkty ADD CONSTRAINT typy_produktu_produkty_fk
FOREIGN KEY (typ)
REFERENCES projekt1.Typy_produktu (typ)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.faktury ADD CONSTRAINT klienci_faktury_fk
FOREIGN KEY (id_klient)
REFERENCES projekt1.klienci (id_klient)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.dostawy ADD CONSTRAINT dostawcy_dostawy_fk
FOREIGN KEY (nip)
REFERENCES projekt1.dostawcy (nip)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.magazyny ADD CONSTRAINT admin_magazyny_fk
FOREIGN KEY (id_admin)
REFERENCES projekt1.admin (id_admin)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.Produkty ADD CONSTRAINT magazyny_produkty_fk
FOREIGN KEY (id_magazynu)
REFERENCES projekt1.magazyny (id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.dostawy ADD CONSTRAINT magazyny_dostawy_fk
FOREIGN KEY (id_magazynu)
REFERENCES projekt1.magazyny (id_magazynu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;

ALTER TABLE projekt1.faktury ADD CONSTRAINT produkty_faktury_fk
FOREIGN KEY (id_magazynu, id_produktu)
REFERENCES projekt1.Produkty (id_magazynu, id_produktu)
ON DELETE NO ACTION
ON UPDATE NO ACTION
NOT DEFERRABLE;