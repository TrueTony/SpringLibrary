# SpringLibrary
MVC приложение реализующее функционал библиотеки.

## Инструменты
- Java (11)
- Spring
- Hibernate
- Thymeleaf
- Postgresql
- Tomcat

## Реализация
Реализация проекта через паттерны MVC и DAO.<br>
В БД создаются таблицы Person и Book в отношении один ко многим.<br>
В проекте создаются два аналогичные сущности и DAO для них, вся работа с БД происходит через сервисы.<br>
Все команды выполняются средствами Hibernate и Spring Data JPA. DAO оставлен для более сложных запросов.<br>

## Функционал
- [x] Страницы добавления, изменения и удаления человека и книги.
- [x] Страницы со списком всех людей и книг (люди и книги кликабельные - при клике осуществляется переход на их страницу).
- [x] На странице человека показаны значения его полей и список книг, которые он взял. Если человек не взял ни одной книги, то выводится соответствующий текст.
- [x] На странице книги показаны значения полей этой книги и имя человека, который взял эту книгу. Если эта книга не была никем взята, то выводится соответствующий текст
- [x] Функционал "освобождения книги" на странице книги. После нажатия на кнопку книга снова становится свободной и пропадает из списка книг человека.
Так же если книга свободна, есть выпадающий список со всеми людьми и кнопка "Назначить книгу". 
После нажатия на эту кнопку, книга привязывается к выбранному человеку и появляется в его списке книг.
- [x] Все поля валидируются с помощью *@Valid* и *Spring Validator*, если это требуется.

- [x] Пагинация книг.
Метод index() в BooksController принимать в адресной строке два ключа: *page* и *books_per_page*. 
Первый ключ сообщает, какую страницу мы запрашиваем, а второй ключ сообщает, сколько книг должно быть на одной странице.
Нумерация страниц стартует с 0. Если в адресной строке не передаются эти ключи, то
возвращаются все книги.<br>
Пример http://localhost:8080/books?page=1&books_per_page=2

- [x] Сортировка книг по году.
Метод index() в BooksController уметь принимать в адресной строке ключ *sort_by_year*. 
Если он имеет значение true, то выдача будет отсортирована по году. 
Если в адресной строке не передается этот ключ, то книги возвращаются в обычном порядке.<br>
Пример http://localhost:8080/books?sort_by_year=true

- [x] Пагинация и сортировка могут работать одновременно.

- [x] Поиска книги. 
Поиск по начальным буквам названия книги, получаем полное название книги и имя автора. Также, если
книга сейчас находится у кого-то, получаем имя этого человека.

- [x] Автоматическая проверка на то, что человек просрочил возврат книги.
Если человек взял книгу более 10 дней назад и до сих пор не вернул ее, эта книга на странице этого человека подсвечиваться красным цветом.
