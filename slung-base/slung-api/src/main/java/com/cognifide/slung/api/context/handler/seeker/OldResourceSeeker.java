package com.cognifide.slung.api.context.handler.seeker;

import com.day.cq.commons.DiffInfo;
import com.day.cq.commons.DiffService;
import com.google.common.base.Optional;
import javax.annotation.Nullable;
import javax.servlet.ServletRequest;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.apache.sling.api.resource.Resource;

@RequiredArgsConstructor(staticName = "using")
public class OldResourceSeeker {

  @Nullable
  private final ServletRequest request;

  @NonNull
  private final Resource resource;

  public Optional<Resource> seek() {
    return null == request
        ? Optional.<Resource>absent()
        : Optional.fromNullable(DiffInfo.getVersionedResource(resource, request.getParameter(DiffService.REQUEST_PARAM_DIFF_TO)));
  }
}
