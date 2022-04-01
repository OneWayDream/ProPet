import styled from 'styled-components';

const StyledMenuItem = styled.a((props) => `
	color: ${props.color ? props.color : 'black'};
	text-decoration: none;
	padding: 15px;
	&:hover {
		transition: all .15s;
		background-color: #7fff9880;
		border-radius: 50px;
	}
`);

const MenuItem = (props) => {
	const { children,  href, ...otherProps} = props;
	return <StyledMenuItem {...otherProps} href={href}>{children}</StyledMenuItem>
}

export default MenuItem;

