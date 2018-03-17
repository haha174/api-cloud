package com.wen.cloud.util;
import java.util.List;
public class ListResponse<T> extends BaseResponse
{
  private List<T> values;

  public List<T> getValues()
  {
    return values;
  }

  public void setValues(List<T> values)
  {
    this.values = values;
  }
}