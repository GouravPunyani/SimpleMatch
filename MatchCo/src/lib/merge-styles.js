import { pipe, mergeAll, filter, memoize } from 'ramda'

/**
 * Merges an array of objects together skipping over nulls.
 *
 * @param victims A list of objects
 * @return An object.
 */
export const mergeStyles: {} = memoize((...victims: []) =>
  pipe(filter(Boolean), mergeAll)(victims)
)
