insert into artikels(naam, aankoopprijs, verkoopprijs, soort, houdbaarheid, artikelgroepId)
values ('testFood', 1, 1, 'F', 10, (select id from artikelgroepen where naam = 'test'));

insert into artikels(naam, aankoopprijs, verkoopprijs, soort, garantie, artikelgroepId)
values ('testNonFood', 1, 1, 'NF', 10, (select id from artikelgroepen where naam = 'test'));

insert into kortingen (artikelId, vanafAantal, percentage)
values ((select id from artikels where naam = 'testFood'), 5, 10);