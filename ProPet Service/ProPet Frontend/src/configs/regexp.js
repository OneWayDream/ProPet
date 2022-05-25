export const regexs = {
  emailRegex: RegExp('^[a-zA-Z0-9]+@[a-zA-Z0-9]+\\.[A-Za-z]+$'),
  loginRegex: RegExp('^[a-zA-Z0-9\-_]{5,10}'),
  passwordRegex: RegExp('.{8,50}'),
  nameRegex: RegExp('[A-Za-zА-ЯЁа-яё]{2,10}'),
  surnameRegex: RegExp('[A-Za-zА-ЯЁа-яё]{2,10}'),
  cityRegex: RegExp('[A-Za-zА-ЯЁа-яё]{2,20}'),
  telephoneNumberRegex: RegExp('(^$|[0-9{10}])'),
  ageRegex: RegExp('([0-9]{2})'),
  aboutInfoRegex: RegExp('\.{50,1000}'),
}