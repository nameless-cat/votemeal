DELETE FROM VOTE_HISTORY;
DELETE FROM LUNCHES;
DELETE FROM RESTAURANTS;
DELETE FROM USER_ROLES;
DELETE FROM USERS;
ALTER SEQUENCE global_seq RESTART WITH 1;

INSERT INTO USERS (NAME, EMAIL, PASSWORD, REGISTERED) VALUES
  ('Admin', 'admin@gmail.com', '$2a$10$srsHIKm25IaGQL1wzYq2J.Wfp6FdcAV7rPjmFcg6A9w6CDl2mZtl6', '2015-01-01 00:00:00'),
  ('Maria', 'maria@yandex.ru', '$2a$10$BEhirOeCymBORjnULE2UW.mjiXydyEYY6O3hdC6jGBZ5.lhM5.k6e', '2015-04-23 17:15:02'),
  ('Oleg', 'oleg@yandex.ru', '$2a$10$L73oV.zNLNDWKbqLjG59YuubQpLi3hyUg6ex0Q5lFhpAlrw8OKIKq', '2015-05-15 14:15:00'),
  ('User', 'user@gmail.com', '$2a$10$tyTQDJMtNOdqaOLlNdZU4OtF0fIPeoLG3oZwcmwAm1Zw8OVcmGmBS', '2016-01-01 00:00:00');

INSERT INTO USER_ROLES (role, user_id) VALUES
  ('ROLE_ADMIN', 1),
  ('ROLE_USER', 2),
  ('ROLE_USER', 3),
  ('ROLE_USER', 4);

INSERT INTO RESTAURANTS (NAME, STREET, BUILDING, ROUTE_GUIDE, WORK_FROM, WORK_UNTIL, FOR_VOTE) VALUES
  ('Макдоналдс', 'МКАД, 47-й километр', '20', 'ТЦ Наш', '9:00:00', '22:30:00', FALSE),
  ('Крошка Картошка', 'МКАД, 47-й километр', '20', 'ТЦ Наш', '9:00:00', '22:00:00', FALSE),
  ('Шоколадница', 'Солнцевский проспект', '21', 'ТЦ Столица', '9:00:00', '22:00:00', TRUE),
  ('Бенджамин', '50 лет Октября', '2', '', '9:00:00', '0:00:00', FALSE),
  ('Subway', '50 лет Октября', '10', '', '9:00:00', '22:00:00', TRUE);

INSERT INTO LUNCHES (NAME, PRICE, RESTAURANT_ID, DESCRIPTION) VALUES
  ('Гриль Гурмэ', 150, 5,
   'Рубленая говядина с добавлением свинины, обжаренная на гриле. Острый перец. Маринованые огурцы. Хрустящая булочка'),
  ('Чизбургер Фреш', 120, 5,
   'Сочная котлета из 100% говядины, сыр со вкусом бекона и перца. Ломтик свежего помидора салат и острый соус. Макчикен на регулярной булочке'),
  ('Картошка с копченостями', 125, 6, 'Энергерическая ценность на 100г: 116,16 Ккал'),
  ('Окрошка на квасе', 170, 6, 'Энергерическая ценность на 100г: 48 Ккал'),
  ('Эспрессо 30мл', 114, 7, 'Подается с бокалом воды'),
  ('Капучиино "Карамель" 32мл', 269, 7, 'С карамельным топпингом и ванильным сиропом'),
  ('Первое', 100, 8, 'Борщ со сметаной. Подается с чесночными гренками'),
  ('Второе', 170, 8, 'Куриная отбивня в кляре. Картофельное пюре'),
  ('Альпийский Саб', 150, 9,
   'Сочные колбаски с пряностями. Листья свежего салата с ломтиками помидоров и огурцов. Свежеиспеченый хлеб'),
  ('Сабвэй Клаб', 120, 9,
   'Соблазнительное сочетание ростбифа, ветчины и индейки, свежие овощи по Вашему выбору на свежевыпеченном хлебе');

INSERT INTO VOTE_HISTORY (RESTAURANT_ID, USER_ID, DATE) VALUES
  (6, 2, '2014-05-20'),
  (6, 3, '2014-05-20'),
  (7, 2, current_date),
  (7, 3, current_date),
  (9, 4, current_date);
