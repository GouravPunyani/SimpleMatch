// @flow

import React from 'react'
import { TouchableOpacity, StyleSheet } from 'react-native'
import { Text } from '../..'
import { spacing, color } from '../../theme'

export type ProductTabBarButtonProps = {
  text: string,
  selected: boolean,
  onPress: () => void,
  style?: any,
  rightBorder?: boolean
}

export function ProductTabBarButton (props: ProductTabBarButtonProps): React.Children {
  const style = [styles.wrapper]
  if (props.style) {
    style.push(props.style)
  }
  if (props.rightBorder) {
    style.push(styles.rightBorder)
  }
  if (props.selected) {
    style.push(styles.selected)
  }
  return (
    <TouchableOpacity style={style} onPress={props.onPress}>
      <Text
        small
        style={styles.text}
        text={props.text}
        white={props.selected}
      />
    </TouchableOpacity>
  )
}

const styles = StyleSheet.create({
  wrapper: {
    flex: 1,
    paddingVertical: spacing.small,
    justifyContent: 'center',
    alignItems: 'center'
  },
  selected: {
    backgroundColor: color.line
  },
  rightBorder: {
    borderRightWidth: 1,
    borderRightColor: color.line
  }
})
