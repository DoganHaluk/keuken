insert into artikels(naam, aankoopprijs, verkoopprijs, soort, houdbaarheid) values ('testFood', 1, 1, 'F', 10);
insert into artikels(naam, aankoopprijs, verkoopprijs, soort, garantie) values ('testNonFood', 1, 1, 'NF', 10);
insert into kortingen (artikelId, vanafAantal, percentage) VALUES ((select id from artikels where naam='testFood'),5, 10);