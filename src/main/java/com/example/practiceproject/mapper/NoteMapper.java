package com.example.practiceproject.mapper;
import com.example.practiceproject.dto.CreationNoteRequest;
import com.example.practiceproject.dto.NoteResponse;
import com.example.practiceproject.entity.Note;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

import java.util.List;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public interface NoteMapper {

    @Mapping(target = "textValue", source = "text")
    Note toEntity(CreationNoteRequest request);

    @Mapping(target = "text", source = "textValue")
    @Mapping(target = "authorName", source = "author.authorName")
    @Mapping(target = "authorSurname", source = "author.authorSurname")
    NoteResponse toResponse(Note note);

    List<NoteResponse> toResponseList(List<Note> notes);

}
