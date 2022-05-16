import Template from "../template"

const NotFoundElement = (props) => {
  const body =
    <div style={{ display: 'flex', justifyContent: 'center', fontSize: '2vw' }}>
      Запрашиваемой страницы не существует
    </div>
  return <Template body={body} />
}


export default NotFoundElement