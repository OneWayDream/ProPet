import Template from "../template/";
import Image from "../../atoms/image/";
import profilePic from "../../../img/profile_pic.png";
import settingPic from "../../../img/settings_picture.png";
import OutputItem from "../../atoms/outputItem";

const ProfilePage = () => {
  const body =
    <div className="profileContainer">
      <div style={{ display: 'flex' }}>
        <div>
          <Image image={profilePic} width="15vw" />
        </div>
        <div style={{ padding: '0 1vw' }}>
          <a href="/settings"><Image image={settingPic} width="3vw" /></a>
        </div>
        <div>
          <div style={{ display: 'flex', height: '100%' }}>
            <div>
              <OutputItem>Логин: TestLogin</OutputItem>
              <OutputItem>Почта: TestMail@testmail.gg</OutputItem>
              <OutputItem>Город: Тестовый Город</OutputItem>
              <OutputItem>Номер телефона: +7 (800) 555-35-35</OutputItem>
            </div>
            <div>
              <OutputItem>Имя: Тестовое имя</OutputItem>
              <OutputItem>Фамилия: Тестовая фамилия</OutputItem>
              <OutputItem>Статус сиделки: включен</OutputItem>
            </div>
          </div>
          <hr style={{ height: '1px', background: '#333', border: 'none', backgroundImage: 'linear-gradient(to right, #ccc, #333, #ccc)', margin: '0' }} />
        </div>
      </div>
    </div>
  return (
    <Template body={body} />
  )
}


export default ProfilePage;