import profilePic from "../../../img/profile_pic.png";
import Image from "../image";

const SearchItem = (props) => {
  const sitter = props.sitter
  const reviewCount = sitter.rateFive + sitter.rateFour + sitter.rateThree + sitter.rateTwo + sitter.rateOne
  return (<>
    <div className="searchItem" style={{ display: 'flex', fontSize: '1.5vw' }}>
      <div>
        <Image image={profilePic} width='10vw' />
      </div>
      <div style={{ display: 'flex', flexDirection: 'column', justifyContent: 'center', alignItems: 'center', width: '100%' }}>
        <div>{sitter.name} {sitter.surname}</div>
        <div>Город: {sitter.city ? sitter.city : 'Не указан'}</div>
        <div>Возраст: {sitter.age ? sitter.age : 'Не указан'}</div>
        <div>Количество отзывов: {reviewCount ? reviewCount : 'Нет данных'}</div>
        <div>Рейтинг: {sitter.rating ? sitter.rating + '/5' : 'Нет даных'}</div>
      </div>
    </div>
  </>)
}

export default SearchItem