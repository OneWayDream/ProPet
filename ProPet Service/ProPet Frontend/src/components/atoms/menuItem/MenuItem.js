import styled from 'styled-components';

const StyledMenuItem = styled.a((props) => `
	color: ${props.color ? props.color : 'black'};
	text-decoration: none;
`);

const MenuItem = (props) => {
	const { children,  href, ...otherProps} = props;
	return <StyledMenuItem {...otherProps} href={href}>{children}</StyledMenuItem>
}

export default MenuItem;

